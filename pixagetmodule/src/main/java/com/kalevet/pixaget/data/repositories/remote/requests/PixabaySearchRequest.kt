package com.kalevet.pixaget.data.repositories.remote.requests

import com.kalevet.pixaget.data.repositories.remote.responses.PixabaySearchResult

interface PixabaySearchRequest<T : PixabaySearchResult> {

    var apiKey: String

    fun buildRequestUrl(): String

    fun buildRequestQueryMap(): Map<String, String>

    fun getResponseClassType(): Class<T>

}