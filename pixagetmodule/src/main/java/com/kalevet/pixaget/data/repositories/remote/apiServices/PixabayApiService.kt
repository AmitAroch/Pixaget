package com.kalevet.pixaget.data.repositories.remote.apiServices

import com.kalevet.pixaget.data.repositories.remote.requests.PixabaySearchRequest
import com.kalevet.pixaget.data.repositories.remote.responses.PixabaySearchResult

interface PixabayApiService {

    suspend fun sendRequest(request: PixabaySearchRequest<*>): PixabaySearchResult<*>?

}