package com.kalevet.pixaget.data.repositories.remote.responses

import com.kalevet.pixaget.data.models.image.ImageItem

data class ImageSearchResult(
    val total: Int,
    val totalHits: Int,
    var hits: List<ImageItem>
) : PixabaySearchResult