package com.example.android.marsphotos.network.retrofit

import com.example.android.marsphotos.data.MarsPhoto
import com.example.android.marsphotos.network.model.MarsPhotoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Path

interface MarsApiService {
    /**
     * Returns a [List] of [MarsPhotoDto] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhotoDto>
//    @DELETE("delete_photo/photos")
//    suspend fun deletePhoto(@Path("photoId") photoId: String): List<MarsPhoto>

    @HTTP(method = "DELETE", path = "delete_photo/photos", hasBody = true)
    suspend fun deletePhoto(@Body photoIds: List<MarsPhoto>):Unit
}