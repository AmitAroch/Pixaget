package com.kalevet.pixagetapp.data.repositories.local.room

import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.kalevet.pixaget.data.repositories.remote.requests.ImageSearchRequest
import com.kalevet.pixaget.data.repositories.remote.requests.PixabaySearchRequest
import com.kalevet.pixagetapp.data.models.image.ImageItemCash

@Dao
abstract class ImageItemCashDao {

    @Transaction
    @RawQuery(observedEntities = [ImageItemCash::class])
    abstract fun pagingSource(query: SupportSQLiteQuery): PagingSource<Int, ImageItemCash>

    fun pagingSourceByRequest(imageSearchRequest: ImageSearchRequest): PagingSource<Int, ImageItemCash> {
        val query = toRawQuery(
            imageSearchRequest,
            "SELECT * FROM ImageItemPagerCash WHERE",
            " ORDER BY primaryKey"//" ORDER BY lastUpdate ASC, image_id"
        )
        return pagingSource(query)
    }

    @Transaction
    @RawQuery
    abstract suspend fun deleteRequest(query: SupportSQLiteQuery): Int

    suspend fun deleteByRequest(imageSearchRequest: ImageSearchRequest): Int{
        val query = toRawQuery(
            imageSearchRequest,
            "DELETE FROM ImageItemPagerCash WHERE",
            "",
        )
        return deleteRequest(query)
    }

    @Transaction
    @RawQuery
    abstract suspend fun oldestUpdate(query: SupportSQLiteQuery): Long?

    suspend fun oldestUpdateByRequest(imageSearchRequest: ImageSearchRequest): Long?{
        val query = toRawQuery(
            imageSearchRequest,
            "SELECT MIN(lastUpdate) FROM ImageItemPagerCash WHERE",
            ""
        )
        return oldestUpdate(query)
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllImages(imagesList: List<ImageItemCash>)

    @Transaction
    @Query("SELECT * FROM ImageItemCash WHERE id= :imageID")
    abstract suspend fun getCurrentImage(imageID: Int): ImageItemCash


    private fun toRawQuery(
        isr: ImageSearchRequest,
        prefix: String,
        sufix: String,
    ): SimpleSQLiteQuery {
        val builder = StringBuilder()
        builder.append(prefix)
        var first = true
        if (isr.query.isNotEmpty()) {
            first = false
            builder.append(" query='${isr.query}'")
        }

        if (isr.image_type != PixabaySearchRequest.ImageTypes.all) {
            if (first) first = false else builder.append(" AND")
            builder.append(" type='${isr.image_type.name}'")
        }

        if (isr.orientation != PixabaySearchRequest.Orientation.all) {
            if (first) first = false else builder.append(" AND")
            builder.append(" orientation='${isr.orientation.name}'")
        }

        if (isr.category != PixabaySearchRequest.Category.all) {
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
            builder.append(" editors_choice=" + if(isr.editors_choice) 1 else 0)
        }

        if (isr.safeSearch) {
            if (first) first = false else builder.append(" AND")
            builder.append(" safesearch=" + if(isr.safeSearch) 1 else 0)
        }

        if (isr.order != PixabaySearchRequest.Order.popular) {
            if (!first) builder.append(" AND")
            builder.append(" order='${isr.order.name}'")
        }

        builder.append(sufix)

        return SimpleSQLiteQuery(builder.toString())
    }
}
