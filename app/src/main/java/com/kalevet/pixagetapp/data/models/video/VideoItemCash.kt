package com.kalevet.pixagetapp.data.models.video

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kalevet.pixaget.data.models.video.VideoItem
import com.kalevet.pixaget.data.models.video.VideoResolutionDetails
import com.kalevet.pixaget.data.repositories.remote.requests.VideoSearchRequest

@Entity
data class VideoItemCash(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val lastUpdate: Long = System.currentTimeMillis(),
    val videoId: Int,
    val type: String?,
    val duration: Int?,
    val title: String?,
    val subTitle: String?,
    val query: String?,
    val category: String?,
    val editors_choice: Boolean = false,
    val safeSearch: Boolean = false,
    val isLatest: Boolean = false,
    val picture_id: String?,            //?
    var pictureURL: String?,
    val videoUrl: String?,
    val videoWidth: Int?,
    val videoHeight: Int?,
    val user_id: Int?,                  //?
    val userImageURL: String?
) {
    companion object {
        fun create(item: VideoItem, request: VideoSearchRequest): VideoItemCash {
            // Get the url path of the largest sized video
            val largestVideoAvailable: VideoResolutionDetails? =
                item.videos?.large?.let { if (it.url.isNotBlank()) it else null }
                    ?: item.videos?.medium?.let { if (it.url.isNotBlank()) it else null }
                    ?: item.videos?.small?.let { if (it.url.isNotBlank()) it else null }
                    ?: item.videos?.tiny?.let { if (it.url.isNotBlank()) it else null }

            return VideoItemCash(
                videoId = item.id,
                type = item.type,
                duration = item.duration,
                title = item.tags,
                subTitle = "${item.user} · ${item.views} views · ${item.likes} likes · ${item.downloads} downloads",
                query = request.query,
                category = request.category.name,
                editors_choice = request.editors_choice,
                safeSearch = request.safeSearch,
                isLatest = request.order.value,
                picture_id = item.picture_id,
                pictureURL = item.pictureURL,
                videoUrl = largestVideoAvailable?.url,
                videoWidth = largestVideoAvailable?.width,
                videoHeight = largestVideoAvailable?.height,
                user_id = item.user_id,
                userImageURL = item.userImageURL,
            )
        }
    }
}
