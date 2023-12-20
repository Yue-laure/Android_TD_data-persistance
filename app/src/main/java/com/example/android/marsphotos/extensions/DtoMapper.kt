package com.example.android.marsphotos.extensions

import android.net.Uri
import com.example.android.marsphotos.data.MarsPhoto
import com.example.android.marsphotos.network.model.MarsPhotoDto

fun MarsPhotoDto.toMarsPhoto() = MarsPhoto(
    id = id,
    url = imgSrcUrl,
    isSelected = false,
    uri=null
)
