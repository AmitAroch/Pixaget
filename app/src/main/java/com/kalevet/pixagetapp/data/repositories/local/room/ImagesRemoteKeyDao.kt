package com.kalevet.pixagetapp.data.repositories.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.kalevet.pixaget.data.repositories.remote.requests.ImageSearchRequest
import com.kalevet.pixaget.data.repositories.remote.requests.PixabaySearchRequest.*
import com.kalevet.pixagetapp.data.repositories.paging.ImageRemoteKey

@Dao
abstract class ImagesRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertOrReplace(remoteKey: ImageRemoteKey)

    @RawQuery
    abstract suspend fun remoteKeyByQuery(query: SupportSQLiteQuery): ImageRemoteKey

    suspend fun remoteKeyByRequest(imageSearchRequest: ImageSearchRequest): ImageRemoteKey {
        val query = toRqwQuery(
            imageSearchRequest,
            "SELECT * FROM images_remote_keys WHERE",
        )
        return remoteKeyByQuery(query)
    }

    @RawQuery
    abstract suspend fun deleteByQuery(query: SupportSQLiteQuery): Int

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
            builder.append(" query='${isr.query}'")
        }

        if (isr.image_type != ImageTypes.all) {
            if (first) first = false else builder.append(" AND")
            builder.append(" type='${isr.image_type.name}'")
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
            builder.append(" min_width=${isr.min_height}")
        }

        if (isr.editors_choice) {
            if (first) first = false else builder.append(" AND")
            builder.append(" editors_choice=" + if (isr.editors_choice) 1 else 0)
        }

        if (isr.safeSearch) {
            if (first) first = false else builder.append(" AND")
            builder.append(" safesearch=" + if (isr.safeSearch) 1 else 0)
        }

        if (isr.order != Order.popular) {
            if (!first) builder.append(" AND")
            builder.append(" order='${isr.order.name}'")
        }
        return SimpleSQLiteQuery(builder.toString())
    }
}