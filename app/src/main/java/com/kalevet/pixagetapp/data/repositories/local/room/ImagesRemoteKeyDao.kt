package com.kalevet.pixagetapp.data.repositories.local.room

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.kalevet.pixaget.data.repositories.remote.requests.ImageSearchRequest
import com.kalevet.pixaget.data.repositories.remote.requests.PixabaySearchRequest.*
import com.kalevet.pixagetapp.data.repositories.paging.ImageRemoteKey

@Dao
abstract class ImagesRemoteKeyDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertOrReplace(remoteKey: ImageRemoteKey)

    suspend fun insertOrReplaceByRequest(
        imageSearchRequest: ImageSearchRequest,
        prevPage: Int?,
        nextPage: Int?
    ) {
        val key = remoteKeyByRequest(imageSearchRequest)?.copy(
            prevPage = prevPage,
            nextPage = nextPage
        ) ?: ImageRemoteKey.fromRequest(imageSearchRequest,prevPage, nextPage)
        insertOrReplace(key)
    }

    @Transaction
    @RawQuery
    protected abstract suspend fun remoteKeyByQuery(query: SupportSQLiteQuery): ImageRemoteKey?

    suspend fun remoteKeyByRequest(imageSearchRequest: ImageSearchRequest): ImageRemoteKey? {
        val query = toRqwQuery(
            imageSearchRequest,
            "SELECT * FROM images_remote_keys WHERE",
        )
        return remoteKeyByQuery(query)
    }

    @Transaction
    @RawQuery
    protected abstract suspend fun deleteByQuery(query: SupportSQLiteQuery): Int

    suspend fun deleteByRequest(imageSearchRequest: ImageSearchRequest): Int {
        val query = toRqwQuery(
            imageSearchRequest,
            "DELETE FROM images_remote_keys WHERE",
        )
        return deleteByQuery(query)
    }

    private fun toRqwQuery(
        isr: ImageSearchRequest,
        prefix: String,
    ): SimpleSQLiteQuery {
        val builder = StringBuilder()
        builder.append(prefix)
        var first = true
        if (isr.query.isNotEmpty()) {
            first = false
            builder.append(" `query`='${isr.query}'")
        }

        if (isr.image_type != ImageTypes.all) {
            if (first) first = false else builder.append(" AND")
            builder.append(" image_type='${isr.image_type.name}'")
        }

        if (isr.orientation != Orientation.all) {
            if (first) first = false else builder.append(" AND")
            builder.append(" orientation='${isr.orientation.name}'")
        }

        if (isr.category != Category.all) {
            if (first) first = false else builder.append(" AND")
            builder.append(" category='${isr.category.name}'")
        }

        if (isr.min_width != 0) {
            if (first) first = false else builder.append(" AND")
            builder.append(" min_width=${isr.min_width}")
        }

        if (isr.min_height != 0) {
            if (first) first = false else builder.append(" AND")
            builder.append(" min_height=${isr.min_height}")
        }

        if (isr.editors_choice) {
            if (first) first = false else builder.append(" AND")
            builder.append(" editors_choice=" + if (isr.editors_choice) 1 else 0)
        }

        if (isr.safeSearch) {
            if (first) first = false else builder.append(" AND")
            builder.append(" safeSearch=" + if (isr.safeSearch) 1 else 0)
        }

        if (isr.order != Order.popular) {
            if (!first) builder.append(" AND")
            builder.append(" `order`='${isr.order.name}'")
        }
        return SimpleSQLiteQuery(builder.toString())
    }
}