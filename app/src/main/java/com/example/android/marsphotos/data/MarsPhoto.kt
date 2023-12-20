package com.example.android.marsphotos.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class MarsPhoto(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0,
    val id: String,
    val url: String,
    var isSelected: Boolean = false,
    val uri: Uri? = null
)