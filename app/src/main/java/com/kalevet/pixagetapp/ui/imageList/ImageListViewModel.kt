package com.kalevet.pixagetapp.ui.imageList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.kalevet.pixaget.Pixaget
import com.kalevet.pixaget.data.repositories.remote.requests.ImageSearchRequest
import com.kalevet.pixaget.data.repositories.remote.requests.PixabaySearchRequest
import com.kalevet.pixagetapp.data.models.image.ImageItemCash
import com.kalevet.pixagetapp.data.repositories.local.room.PixabayCashDatabaseProvider
import com.kalevet.pixagetapp.data.repositories.paging.PixabayImagesRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class ImageListViewModel @Inject constructor(
    application: Application,
    private val pixaget: Pixaget
) : AndroidViewModel(application) {

    private val database = PixabayCashDatabaseProvider.getPixabayCashDatabase(application)
    private val imageCashDao = database.imageCashDao()
    private val imageSearchRequest = ImageSearchRequest(query = "girl", image_type = PixabaySearchRequest.ImageTypes.illustration)

    private val _imagesFlow =
        MutableStateFlow<PagingData<ImageItemCash>>(PagingData.empty())
    val imagesFlow: Flow<PagingData<ImageItemCash>> = _imagesFlow.asStateFlow()

    init {
        imageSearchRequest.apiKey = "23570191-4b9d123947a3c70a11419dfbe"
        initNoQueryImagesFlow(imageSearchRequest)
    }

    private fun initNoQueryImagesFlow(imageSearchRequest: ImageSearchRequest) {
        val remoteMediator = PixabayImagesRemoteMediator(
            networkService = pixaget,
            db = database,
            imageSearchRequest
        )
        viewModelScope.launch {
            initPager(remoteMediator, imageSearchRequest)
        }
    }

    fun loadImages(imageSearchRequest: ImageSearchRequest) {
        initImagesFlowByQuery(imageSearchRequest)
    }

    private fun initImagesFlowByQuery(imageSearchRequest: ImageSearchRequest) {
        val remoteMediator =
            PixabayImagesRemoteMediator(
                networkService = pixaget,
                db = database,
                imageSearchRequest = imageSearchRequest,
            )
        viewModelScope.launch {
            initPager(remoteMediator, imageSearchRequest)
        }
    }

    private suspend fun initPager(
        remoteMediator: RemoteMediator<Int, ImageItemCash>,
        imageSearchRequest: ImageSearchRequest
    ) {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20,
                prefetchDistance = 10,
            ),
            remoteMediator = remoteMediator
        ) {
            imageCashDao.pagingSourceByRequest(imageSearchRequest)
        }
        pager.flow.cachedIn(viewModelScope).collectLatest {
            _imagesFlow.value = it
        }

    }
}