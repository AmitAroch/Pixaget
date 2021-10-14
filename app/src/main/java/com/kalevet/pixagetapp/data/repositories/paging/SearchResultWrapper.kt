package com.kalevet.pixagetapp.data.repositories.paging

import com.kalevet.pixaget.data.repositories.remote.responses.PixabaySearchResult

class  SearchResultWrapper<T: PixabaySearchResult<*>> constructor(
    val searchResult: T,
    val nextPage: Int?,
    val prevPage: Int?
){
    companion object{
        fun create(searchResult: PixabaySearchResult<*>, resultsPerPage: Int, currentPage: Int): SearchResultWrapper<*>{
            val prevPageNumber = if (currentPage == 1) null else currentPage.minus(1)
            val numberOfHits = searchResult.hits.size
            val nextPageNumber = if (numberOfHits < resultsPerPage) null else currentPage.plus(1)
            return SearchResultWrapper(searchResult, nextPageNumber, prevPageNumber)
        }
    }
}