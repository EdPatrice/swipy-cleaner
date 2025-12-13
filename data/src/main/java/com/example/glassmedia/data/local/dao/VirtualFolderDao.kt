package com.example.glassmedia.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.glassmedia.data.local.entity.VirtualFolder
import kotlinx.coroutines.flow.Flow

@Dao
interface VirtualFolderDao {
    @Query("SELECT * FROM virtual_folders")
    fun getAll(): Flow<List<VirtualFolder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(folder: VirtualFolder)

    @Update
    suspend fun update(folder: VirtualFolder)
}
