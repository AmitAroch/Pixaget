package com.kalevet.pixagetapp.data.repositories.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kalevet.pixaget.Pixaget
import com.kalevet.pixaget.data.models.video.VideoItem
import com.kalevet.pixaget.data.repositories.remote.requests.VideoSearchRequest
import com.kalevet.pixagetapp.data.models.video.VideoItemCash
import com.kalevet.pixagetapp.data.repositories.local.room.PixabayCashDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.IOException
import java.util.concurrent.TimeUnit

private const val INITIAL_PAGE_NUM = 1

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class PixabayVideosRemoteMediator constructor(
    private val networkService: Pixaget,
    private val db: PixabayCashDatabase,
    private var videoSearchRequest: VideoSearchRequest
) : RemoteMediator<Int, VideoItem>() {
    private val videoCashDao = db.videoCashDao()
    private val remoteKeyDao = db.videosRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, VideoItem>
    ): MediatorResult {
        return try {
            // The network load method takes an optional String
            // parameter. For every page after the first, pass the String
            // token returned from the previous page to let it continue
            // from where it left off. For REFRESH, pass null to load the
            // first page.
            val nextPage = when (loadType) {
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
                        remoteKeyDao.remoteKeyByRequest(videoSearchRequest)
                    }
                    if (remoteKey == null) {
                        INITIAL_PAGE_NUM
                    } else remoteKey.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                }
            }

            // Suspending network load via Retrofit. This doesn't need to
            // be wrapped in a withContext(Dispatcher.IO) { ... } block
            // since Retrofit's Coroutine CallAdapter dispatches on a
            // worker thread.
            videoSearchRequest.page = nextPage
            val response = ResponseWrapper(
                networkService.sendSearchRequest(videoSearchRequest),
                videoSearchRequest.page,
                videoSearchRequest.per_page
            )
            response.result ?: return MediatorResult.Error(IOException())

            // Store loaded data, and next key in transaction, so that
            // they're always consistent.
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteByRequest(videoSearchRequest)
                    videoCashDao.deleteByRequest(videoSearchRequest)
                }


                // Update VideoRemoteKey for this query.
                remoteKeyDao.insertOrReplaceByRequest(
                    videoSearchRequest,
                    response.prevPage,
                    response.nextPage
                )

                // Insert new images into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                response.result.hits.map { videoItem ->
                    VideoItemCash.create(
                        videoItem,
                        videoSearchRequest
                    )
                }.let { videoItemCashList ->
                    videoCashDao.insertAllVideos(
                        videoItemCashList
                    )
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = response.nextPage == null
            )

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.DAYS.convert(1, TimeUnit.MILLISECONDS)
        videoCashDao.oldestUpdateByRequest(videoSearchRequest)
            ?: return InitializeAction.LAUNCH_INITIAL_REFRESH
        return if (System.currentTimeMillis() - videoCashDao.oldestUpdateByRequest(
                videoSearchRequest
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