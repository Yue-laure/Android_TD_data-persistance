package com.example.android.marsphotos.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MarsPhotoDAO {
    @Query("SELECT * FROM photos")
    fun getAll(): List<MarsPhoto>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg photo: MarsPhoto)
    @Update
    fun update(photo: MarsPhoto)
    @Delete
    fun delete(photo: MarsPhoto)
    @Query("DELETE FROM photos")
    fun deleteAll()
}