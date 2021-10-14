package com.kalevet.pixagetapp.data.repositories.paging

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kalevet.pixaget.data.repositories.remote.requests.VideoSearchRequest


@Entity(tableName = "videos_remote_keys")
data class VideoRemoteKey(
    @PrimaryKey
    @Embedded(prefix = "req_")
    val label: VideoSearchRequest,
    val prevPage: Int?,
    val nextPage: Int?
)