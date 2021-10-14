package com.example.pixabayLibraryApp.ui.mainActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kalevet.pixaget.Pixaget
import com.kalevet.pixaget.data.repositories.remote.requests.ImageSearchRequest
import com.kalevet.pixaget.data.repositories.remote.requests.VideoSearchRequest
import com.kalevet.pixaget.data.repositories.remote.responses.ImageSearchResult
import com.kalevet.pixaget.data.repositories.remote.responses.VideoSearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val pixaget: Pixaget,
) : AndroidViewModel(application) {

    val images = MutableLiveData<ImageSearchResult?>(null)
    val videos = MutableLiveData<VideoSearchResult?>(null)

    fun sendSearchRequest(request: ImageSearchRequest){
        viewModelScope.launch(Dispatchers.IO) {
            images.postValue(pixaget.sendSearchRequest(request))
        }
    }

    fun sendSearchRequest(request: VideoSearchRequest){
        viewModelScope.launch(Dispatchers.IO) {
            videos.postValue(pixaget.sendSearchRequest(request))
        }
    }

}