package com.example.android.marsphotos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [MarsPhoto::class], version = 2)
@TypeConverters(UriConverter::class)
abstract class DataBasePhoto : RoomDatabase() {
    abstract fun marsPhotoDao() : MarsPhotoDAO
    companion object {
        // SINGLETON
        @Volatile
        private var INSTANCE: DataBasePhoto? = null
        fun getInstance(context: Context): DataBasePhoto {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DataBasePhoto::class.java, "photo_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}