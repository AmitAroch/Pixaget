package com.kalevet.pixagetapp.data.repositories.paging

import com.kalevet.pixaget.data.repositories.remote.responses.PixabaySearchResult

class ResponseWrapper<T:PixabaySearchResult<*>?> constructor(
    val result:T,
    currentPage: Int,
    perPage: Int
){
    val nextPage: Int? = if (result?.hits?.size ?: 0 < perPage) null else currentPage + 1
    val prevPage: Int? = if (currentPage == 1) null else currentPage - 1
}