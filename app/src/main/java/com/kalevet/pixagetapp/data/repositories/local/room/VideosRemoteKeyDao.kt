package com.kalevet.pixagetapp.data.repositories.local.room

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.kalevet.pixaget.data.repositories.remote.requests.PixabaySearchRequest
import com.kalevet.pixaget.data.repositories.remote.requests.VideoSearchRequest
import com.kalevet.pixagetapp.data.repositories.paging.VideoRemoteKey

@Dao
abstract class VideosRemoteKeyDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertOrReplace(remoteKey: VideoRemoteKey)

    suspend fun insertOrReplaceByRequest(
        videoSearchRequest: VideoSearchRequest,
        prevPage: Int?,
        nextPage: Int?
    ) {
        val key = remoteKeyByRequest(videoSearchRequest)?.copy(
            prevPage = prevPage,
            nextPage = nextPage
        ) ?: VideoRemoteKey.fromRequest(videoSearchRequest,prevPage, nextPage)
        insertOrReplace(key)
    }

    @Transaction
    @RawQuery
    protected abstract suspend fun remoteKeyByQuery(query: SupportSQLiteQuery): VideoRemoteKey?

    suspend fun remoteKeyByRequest(videoSearchRequest: VideoSearchRequest): VideoRemoteKey? {
        val query = toRqwQuery(
            videoSearchRequest,
            "SELECT * FROM videos_remote_keys WHERE",
        )
        return remoteKeyByQuery(query)
    }

    @Transaction
    @RawQuery
    protected abstract suspend fun deleteByQuery(query: SupportSQLiteQuery): Int

    suspend fun deleteByRequest(videoSearchRequest: VideoSearchRequest): Int {
        val query = toRqwQuery(
            videoSearchRequest,
            "DELETE FROM videos_remote_keys WHERE",
        )
        return deleteByQuery(query)
    }

    private fun toRqwQuery(
        vsr: VideoSearchRequest,
        prefix: String,
    ): SimpleSQLiteQuery {
        val builder = StringBuilder()
        builder.append(prefix)
        var first = true
        if (vsr.query.isNotEmpty()) {
            first = false
            builder.append(" `query`='${vsr.query}'")
        }

        if (vsr.video_type != PixabaySearchRequest.VideoType.all) {
            if (first) first = false else builder.append(" AND")
            builder.append(" video_type='${vsr.video_type.name}'")
        }

        if (vsr.category != PixabaySearchRequest.Category.all) {
            if (first) first = false else builder.append(" AND")
            builder.append(" category='${vsr.category.name}'")
        }

        if (vsr.min_width != 0) {
            if (first) first = false else builder.append(" AND")
            builder.append(" min_width=${vsr.min_width}")
        }

        if (vsr.min_height != 0) {
            if (first) first = false else builder.append(" AND")
            builder.append(" min_height=${vsr.min_height}")
        }

        if (vsr.editors_choice) {
            if (first) first = false else builder.append(" AND")
            builder.append(" editors_choice=" + if (vsr.editors_choice) 1 else 0)
        }

        if (vsr.safeSearch) {
            if (first) first = false else builder.append(" AND")
            builder.append(" safeSearch=" + if (vsr.safeSearch) 1 else 0)
        }

        if (vsr.order != PixabaySearchRequest.Order.popular) {
            if (!first) builder.append(" AND")
            builder.append(" `order`='${vsr.order.name}'")
        }
        return SimpleSQLiteQuery(builder.toString())
    }

}