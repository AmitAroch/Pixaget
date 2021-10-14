package com.kalevet.pixagetapp.data.repositories.local.room

import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.kalevet.pixaget.data.repositories.remote.requests.PixabaySearchRequest
import com.kalevet.pixaget.data.repositories.remote.requests.VideoSearchRequest
import com.kalevet.pixagetapp.data.models.video.VideoItemCash


@Dao
abstract class VideoItemCashDao {

    @Transaction
    @RawQuery(observedEntities = [VideoItemCash::class])
    abstract fun pagingSource(query: SupportSQLiteQuery): PagingSource<Int, VideoItemCash>

    fun pagingSourceByRequest(videoSearchRequest: VideoSearchRequest): PagingSource<Int, VideoItemCash> {
        val query = toRawQuery(
            videoSearchRequest,
            "SELECT * FROM VideoItemCash WHERE",
            " ORDER BY lastUpdate ASC",
        )
        return pagingSource(query)
    }

    @Transaction
    @RawQuery
    abstract suspend fun deleteRequest(query: SupportSQLiteQuery): Int

    suspend fun deleteByRequest(videoSearchRequest: VideoSearchRequest): Int{
        val query = toRawQuery(
            videoSearchRequest,
            "DELETE FROM VideoItemCash WHERE",
            "",
        )
        return deleteRequest(query)
    }

    @Transaction
    @RawQuery
    abstract suspend fun oldestUpdate(query: SupportSQLiteQuery): Long?

    suspend fun oldestUpdateByRequest(videoSearchRequest: VideoSearchRequest): Long?{
        val query = toRawQuery(
            videoSearchRequest,
            "SELECT MIN(lastUpdate) FROM VideoItemCash WHERE",
            ""
        )
        return oldestUpdate(query)
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllVideos(videos: List<VideoItemCash>)

    @Transaction
    @Query("SELECT * FROM VideoItemCash WHERE id= :videoID")
    abstract suspend fun getCurrentVideo(videoID: Int): VideoItemCash

    private fun toRawQuery(
        vsr: VideoSearchRequest,
        prefix: String,
        sufix: String,
    ): SimpleSQLiteQuery {
        val builder = StringBuilder()
        builder.append(prefix)
        var first = true
        if (vsr.query.isNotEmpty()) {
            first = false
            builder.append(" query='${vsr.query}'")
        }

        if (vsr.video_type != PixabaySearchRequest.VideoType.all) {
            if (first) first = false else builder.append(" AND")
            builder.append(" type='${vsr.video_type.name}'")
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
            builder.append(" min_width=${vsr.min_height}")
        }

        if (vsr.editors_choice) {
            if (first) first = false else builder.append(" AND")
            builder.append(" editors_choice=" + if(vsr.editors_choice) 1 else 0)
        }

        if (vsr.safeSearch) {
            if (first) first = false else builder.append(" AND")
            builder.append(" safesearch=" + if(vsr.safeSearch) 1 else 0)
        }

        if (vsr.order != PixabaySearchRequest.Order.popular) {
            if (!first) builder.append(" AND")
            builder.append(" order='${vsr.order.name}'")
        }

        builder.append(sufix)

        return SimpleSQLiteQuery(builder.toString())
    }
}
