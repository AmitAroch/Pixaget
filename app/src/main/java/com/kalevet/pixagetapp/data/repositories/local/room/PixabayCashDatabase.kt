package com.kalevet.pixagetapp.data.repositories.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kalevet.pixagetapp.data.models.image.ImageItemCash
import com.kalevet.pixagetapp.data.models.video.VideoItemCash
import com.kalevet.pixagetapp.data.repositories.paging.ImageRemoteKey
import com.kalevet.pixagetapp.data.repositories.paging.VideoRemoteKey

@Database(
    entities = [
        ImageItemCash::class,
        VideoItemCash::class,
        VideoRemoteKey::class,
        ImageRemoteKey::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class PixabayCashDatabase : RoomDatabase() {

    abstract fun videoCashDao(): VideoItemCashDao

    abstract fun imageCashDao(): ImageItemCashDao

    abstract fun videosRemoteKeyDao(): VideosRemoteKeyDao

    abstract fun imagesRemoteKeyDao(): ImagesRemoteKeyDao

}