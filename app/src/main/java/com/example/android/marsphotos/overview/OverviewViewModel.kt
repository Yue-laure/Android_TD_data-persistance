package com.example.android.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsphotos.data.MarsPhoto
import com.example.android.marsphotos.repository.MarsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class MarsApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel() : ViewModel() {

    private val repository = MarsRepository()

    private val _status = MediatorLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus> = _status

    private val _photos = MutableLiveData<List<MarsPhoto>>()
    val photos: LiveData<List<MarsPhoto>> = _photos



    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()

        _status.addSource(_photos) {
            _status.value = if (it.isEmpty()) MarsApiStatus.ERROR else MarsApiStatus.DONE
        }
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhotoDto] [List] [LiveData].
     */
    private fun getMarsPhotos() {

        viewModelScope.launch(Dispatchers.IO) {
            _status.postValue(MarsApiStatus.LOADING)
            try {
                _photos.postValue(repository.getPhotos())
            } catch (e: Exception) {
                _photos.postValue(listOf())
            }
        }
    }

//    delete photo selected
fun deletePhotos(photos: List<MarsPhoto>) {
    viewModelScope.launch(Dispatchers.IO) {
        // Delete photos from the database ,mais pas reussir
//        photos.forEach { photo ->
//            repository.deletePhotos(photos)
//        }

        // Update LiveData after deletion
        val currentPhotos = _photos.value.orEmpty().toMutableList()
        val remainingPhotos = currentPhotos.toMutableList()

        // Remove photos from LiveData based on their content (id)
        remainingPhotos.removeAll { photo -> photos.any { it.id == photo.id } }

        // Update LiveData with the remaining photos
        _photos.postValue(remainingPhotos)
    }
}

}