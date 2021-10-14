package com.kalevet.pixagetapp.data.repositories.paging

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kalevet.pixaget.data.repositories.remote.requests.ImageSearchRequest

@Entity(tableName = "images_remote_keys")
data class ImageRemoteKey(
    @PrimaryKey
    @Embedded
    val label: ImageSearchRequest,
    val prevPage: Int?,
    val nextPage: Int?
)