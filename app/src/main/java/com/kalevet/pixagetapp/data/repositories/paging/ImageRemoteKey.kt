package com.kalevet.pixagetapp.data.repositories.paging

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kalevet.pixaget.data.repositories.remote.requests.ImageSearchRequest

@Entity(tableName = "images_remote_keys")
data class ImageRemoteKey(
    @PrimaryKey(autoGenerate = true)
    val key: Int = 0,
    val query: String?,
    val image_type: String?,
    val orientation: String?,
    val category: String?,
    val min_width: Int?,
    val min_height: Int?,
    val editors_choice: Boolean?,
    val safeSearch: Boolean?,
    val order: Boolean?,
    val prevPage: Int?,
    val nextPage: Int?
) {
    companion object {
        fun fromRequest(
            imageSearchRequest: ImageSearchRequest,
            prevPage: Int?,
            nextPage: Int?
        ): ImageRemoteKey {
            return ImageRemoteKey(
                query = imageSearchRequest.query,
                image_type = imageSearchRequest.image_type.name,
                orientation = imageSearchRequest.orientation.name,
                category = imageSearchRequest.category.name,
                min_width = imageSearchRequest.min_width,
                min_height = imageSearchRequest.min_height,
                editors_choice = imageSearchRequest.editors_choice,
                safeSearch = imageSearchRequest.safeSearch,
                order = imageSearchRequest.order.value,
                prevPage = prevPage,
                nextPage = nextPage
            )
        }
    }
}