package com.example.android.marsphotos.repository

import com.example.android.marsphotos.api.MarsApi
import com.example.android.marsphotos.data.MarsPhoto
import com.example.android.marsphotos.data.MarsPhotoDAO
import com.example.android.marsphotos.network.OnlineApiService
import retrofit2.Response

class MarsRepository() {

    private val service: MarsApi = OnlineApiService()
    suspend fun getPhotos() = service.getPhotos()
    suspend fun deletePhotos(photos: List<MarsPhoto>) = service.deletePhoto(photos)

}
