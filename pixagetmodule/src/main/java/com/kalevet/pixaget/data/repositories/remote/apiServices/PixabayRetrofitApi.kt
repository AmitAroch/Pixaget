package com.kalevet.pixaget.data.repositories.remote.apiServices

import com.kalevet.pixaget.data.repositories.remote.responses.ImageSearchResult
import com.kalevet.pixaget.data.repositories.remote.responses.VideoSearchResult
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PixabayRetrofitApi {

    @GET("api/videos/")
    suspend fun getVideos(
        @QueryMap options: Map<String, String>
    ): VideoSearchResult

    @GET("api/")
    suspend fun getImages(
        @QueryMap options: Map<String, String>
    ): ImageSearchResult
}