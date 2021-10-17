package com.kalevet.pixagetapp.data.repositories.paging

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kalevet.pixaget.data.repositories.remote.requests.VideoSearchRequest


@Entity(tableName = "videos_remote_keys")
data class VideoRemoteKey(
    @PrimaryKey(autoGenerate = true)
    val key: Int = 0,
    val query: String?,
    val video_type: String?,
    val category: String?,
    val min_width: Int?,
    val min_height: Int?,
    val editors_choice: Boolean?,
    val safeSearch: Boolean?,
    val order: Boolean?,
    val prevPage: Int?,
    val nextPage: Int?
){
    companion object {
        fun fromRequest(
            videoSearchRequest: VideoSearchRequest,
            prevPage: Int?,
            nextPage: Int?
        ): VideoRemoteKey {
            return VideoRemoteKey(
                query = videoSearchRequest.query,
                video_type = videoSearchRequest.video_type.name,
                category = videoSearchRequest.category.name,
                min_width = videoSearchRequest.min_width,
                min_height = videoSearchRequest.min_height,
                editors_choice = videoSearchRequest.editors_choice,
                safeSearch = videoSearchRequest.safeSearch,
                order = videoSearchRequest.order.value,
                prevPage = prevPage,
                nextPage = nextPage
            )
        }
    }
}