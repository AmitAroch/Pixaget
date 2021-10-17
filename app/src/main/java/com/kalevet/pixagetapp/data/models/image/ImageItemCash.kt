package com.kalevet.pixagetapp.data.models.image

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kalevet.pixaget.data.models.image.ImageItem
import com.kalevet.pixaget.data.repositories.remote.requests.ImageSearchRequest

@Entity //(indices = [Index(value = ["image_id"], unique = true)])
data class ImageItemCash(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val lastUpdate: Long = System.currentTimeMillis(),
    val ImageId: Int,
    val type: String?,
    val title: String?,
    val subTitle: String?,
    val isHorizontal: Boolean?,
    val query: String?,
    val category: String?,
    val editors_choice: Boolean = false,
    val safeSearch: Boolean = false,
    val isLatest: Boolean = false,
    val previewURL: String?,
    val webformatURL: String?,
    val imageURL: String?,
    val imageWidth: Int?,
    val imageHeight: Int?,
    val vectorURL: String?,
    val user_id: Int?,              //?
    val userImageURL: String?
) {
    companion object {
        fun create(item: ImageItem, request: ImageSearchRequest): ImageItemCash {
            return ImageItemCash(
                ImageId = item.id,
                type = item.type,
                title = item.tags,
                subTitle = "${item.user} · ${item.views} views · ${item.likes} likes · ${item.downloads} downloads",
                isHorizontal = request.orientation.value,
                query = request.query,
                category = request.category.name,
                editors_choice = request.editors_choice,
                safeSearch = request.safeSearch,
                isLatest = request.order.value,
                previewURL = item.previewURL,
                webformatURL = item.webformatURL,
                imageURL = item.imageURL,
                imageWidth = item.imageWidth,
                imageHeight = item.imageHeight,
                vectorURL = item.vectorURL,
                user_id = item.user_id,
                userImageURL = item.userImageURL,
            )
        }
    }
}