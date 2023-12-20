package com.example.android.marsphotos.api

import com.example.android.marsphotos.data.MarsPhoto
import retrofit2.Response

interface MarsApi {
    suspend fun getPhotos(): List<MarsPhoto>
    suspend fun deletePhoto(photos: List<MarsPhoto>)

}