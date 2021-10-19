package com.kalevet.pixaget.data.repositories.remote.apiServices

import android.util.Log
import com.kalevet.pixaget.data.repositories.remote.requests.PixabaySearchRequest
import com.kalevet.pixaget.data.repositories.remote.responses.PixabaySearchResult
import com.kalevet.pixaget.exceptions.HttpException
import com.kalevet.pixaget.utill.jsonHandlers.JsonConverter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import okhttp3.internal.closeQuietly
import java.io.IOException

@ExperimentalCoroutinesApi
class OkHttpClientAdapter(
    private val client: OkHttpClient,
    private val jsonConverter: JsonConverter,
    private val debug: Boolean = false,
) : PixabayApiService {

    @Throws(IOException::class, HttpException::class)
    override suspend fun sendRequest(request: PixabaySearchRequest<*>): PixabaySearchResult<*>? {
        var result: PixabaySearchResult<*>? = null
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
                    Log.e(
                        "OkHttpClientAdapter",
                        "Pixabay server failed to response to the request response.code: ${response.code}, response.message: ${response.message}"
                    )
                }
                throw (HttpException(response))
            }
        } catch (e: IOException) {
            result = null
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
                            call.cancel()
                        } catch (e: Throwable) {
                        }
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    if (continuation.isCancelled) return
                    try {
                        call.cancel()
                    } catch (e: Throwable) {
                    }
                    throw e
                    //continuation.resumeWithException(e)
                }
            })
        }
    }

}