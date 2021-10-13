package com.kalevet.pixaget.data.repositories.remote.apiServices

import android.util.Log
import com.kalevet.pixaget.data.repositories.remote.JsonConverter
import com.kalevet.pixaget.data.repositories.remote.requests.PixabaySearchRequest
import com.kalevet.pixaget.data.repositories.remote.responses.PixabaySearchResult
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.internal.closeQuietly
import okio.IOException
import kotlin.coroutines.resumeWithException

@ExperimentalCoroutinesApi
class OkHttpClientAdapter(
    private val client: OkHttpClient,
    private val jsonConverter: JsonConverter,
    private val debug: Boolean = false,
) : PixabayApiService {

    override suspend fun sendRequest(request: PixabaySearchRequest<*>): PixabaySearchResult? {
        var result: PixabaySearchResult? = null
        withContext(IO) {
            val okHttpRequest = Request.Builder()
                .url(request.buildRequestUrl())
                .build()
            try {
                val response = client.newCall(okHttpRequest).await()
                if (response.isSuccessful) {
                    response.body?.charStream()?.let { reader ->
                        result = jsonConverter.convert(reader, request.getResponseClassType())
                        response.closeQuietly()
                    }
                } else {
                    if (debug) {
                        Log.e("Pixabay", "Pixabay server faild to response to the request response.code: ${response.code}, response.message: ${response.message}")
                        result = null
                    } else {
                        result = null
                    }
                }
            } catch (e: IOException) {
                if (debug) {
                    throw e
                } else {
                    // Do nothing
                    result = null
                }
            }
        }
        return result
    }

    /**
     * Suspend extension that allows suspend [Call] inside coroutine.
     *
     * @return Result of request or throw exception
     */
    private suspend fun Call.await(): Response {
        return suspendCancellableCoroutine { continuation ->
            enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(response) {
                        try {
                            cancel()
                        } catch (ex: Throwable) {
                        }
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    if (continuation.isCancelled) return
                    continuation.resumeWithException(e)
                }
            })
        }
    }
}