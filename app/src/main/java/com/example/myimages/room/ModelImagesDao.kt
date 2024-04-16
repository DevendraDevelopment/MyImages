package com.example.myimages.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myimages.models.ModelImages

@Dao
interface ModelImagesDao {
    @Query("SELECT * FROM model_images")
    fun getAllModelImages(): List<ModelImages>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(modelImagesList: List<ModelImages>)
}
