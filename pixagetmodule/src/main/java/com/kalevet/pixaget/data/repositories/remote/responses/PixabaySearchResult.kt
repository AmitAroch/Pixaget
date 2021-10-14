package com.kalevet.pixaget.data.repositories.remote.responses


interface PixabaySearchResult<T>{
    val total: Int
    val totalHits: Int
    var hits: List<T>
}