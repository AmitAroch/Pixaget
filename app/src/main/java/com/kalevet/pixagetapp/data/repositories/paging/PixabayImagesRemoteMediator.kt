package com.kalevet.pixagetapp.data.repositories.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kalevet.pixaget.Pixaget
import com.kalevet.pixaget.data.repositories.remote.requests.ImageSearchRequest
import com.kalevet.pixagetapp.data.models.image.ImageItemCash
import com.kalevet.pixagetapp.data.repositories.local.room.PixabayCashDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.IOException
import java.util.concurrent.TimeUnit


private const val INITIAL_PAGE_NUM = 1

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class PixabayImagesRemoteMediator constructor(
    private val networkService: Pixaget,
    private val db: PixabayCashDatabase,
    private var imageSearchRequest: ImageSearchRequest
) : RemoteMediator<Int, ImageItemCash>() {
    private val imageCashDao = db.imageCashDao()
    private val remoteKeyDao = db.imagesRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageItemCash>
    ): MediatorResult {
        return try {
            // The network load method takes an optional String
            // parameter. For every page after the first, pass the String
            // token returned from the previous page to let it continue
            // from where it left off. For REFRESH, pass null to load the
            // first page.
            imageSearchRequest.page = when (loadType) {
                LoadType.REFRESH -> INITIAL_PAGE_NUM
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                // Query remoteKeyDao for the next VideoRemoteKey.
                LoadType.APPEND -> {
                    val remoteKey = db.withTransaction {
                        remoteKeyDao.remoteKeyByRequest(imageSearchRequest)
                    }
                    // You must explicitly check if the page key is null when
                    // appending, since null is only valid for initial load.
                    // If you receive null for APPEND, that means you have
                    // reached the end of pagination and there are no more
                    // items to load.
                    if (remoteKey.nextPage == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    remoteKey.nextPage
                }
            }

            // Suspending network load via Retrofit. This doesn't need to
            // be wrapped in a withContext(Dispatcher.IO) { ... } block
            // since Retrofit's Coroutine CallAdapter dispatches on a
            // worker thread.
            val response = networkService.sendSearchRequest(imageSearchRequest)
                ?: return MediatorResult.Error(IOException()) // TODO check if needed or just throw the exception from sendSearchRequest

            // Store loaded data, and next key in transaction, so that
            // they're always consistent.
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteByRequest(imageSearchRequest)
                    imageCashDao.deleteByRequest(imageSearchRequest)
                }

                // Update VideoRemoteKey for this query.
                remoteKeyDao.insertOrReplace(
                    ImageRemoteKey(
                        label = imageSearchRequest,
                        prevPage = prevPageNumber(),
                        nextPage = nextPageNumber(response.hits.size)
                    )

                )

                // Insert new images into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                response.hits.map { imageItem ->
                    ImageItemCash.create(
                        imageItem,
                        imageSearchRequest
                    )
                }.let { imageItemCashList ->
                    imageCashDao.insertAllImages(
                        imageItemCashList
                    )
                }
            }


            MediatorResult.Success(
                endOfPaginationReached = nextPageNumber(response.hits.size) == null
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
    }

    private fun prevPageNumber() =
        if (imageSearchRequest.page == 1) null else imageSearchRequest.page - 1

    private fun nextPageNumber(resultsSize: Int) =
        if (resultsSize < imageSearchRequest.per_page) null else imageSearchRequest.page + 1

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.DAYS.convert(1, TimeUnit.MILLISECONDS)
        imageCashDao.oldestUpdateByRequest(imageSearchRequest)
            ?: return InitializeAction.LAUNCH_INITIAL_REFRESH
        return if (System.currentTimeMillis() - imageCashDao.oldestUpdateByRequest(
                imageSearchRequest
            )!! >= cacheTimeout
        ) {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

}