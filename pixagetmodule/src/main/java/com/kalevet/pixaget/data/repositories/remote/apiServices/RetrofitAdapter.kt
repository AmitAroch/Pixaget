package com.kalevet.pixaget.data.repositories.remote.apiServices

import com.kalevet.pixaget.data.repositories.remote.requests.PixabaySearchRequest
import com.kalevet.pixaget.data.repositories.remote.responses.ImageSearchResult
import com.kalevet.pixaget.data.repositories.remote.responses.PixabaySearchResult
import com.kalevet.pixaget.data.repositories.remote.responses.VideoSearchResult
import com.kalevet.pixaget.utill.PIXABAY_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit


class RetrofitAdapter(
    retrofitBuilder: Retrofit.Builder,
    jsonConverterFactory: Converter.Factory,
    okHttpClient: OkHttpClient? = null,
) : PixabayApiService {

    private val apiService: PixabayRetrofitApi

    init {
        retrofitBuilder.baseUrl(PIXABAY_BASE_URL)
            .addConverterFactory(jsonConverterFactory)

        okHttpClient?.let {
            retrofitBuilder.client(it)
        }

        apiService = retrofitBuilder.build()
            .create(PixabayRetrofitApi::class.java)
    }

    override suspend fun sendRequest(request: PixabaySearchRequest<*>): PixabaySearchResult<*>? {
        val result: PixabaySearchResult<*>? = when (request.getResponseClassType()) {
            ImageSearchResult::class.java -> apiService.getImages(request.buildRequestQueryMap())
            VideoSearchResult::class.java -> apiService.getVideos(request.buildRequestQueryMap())
            else -> null
        }

        return result
    }

}