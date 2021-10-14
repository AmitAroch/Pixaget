package com.kalevet.pixaget.data.repositories.remote.responses

import com.kalevet.pixaget.data.models.image.ImageItem

data class ImageSearchResult(
    override val total: Int,
    override val totalHits: Int,
    override var hits: List<ImageItem>
) : PixabaySearchResult<ImageItem>