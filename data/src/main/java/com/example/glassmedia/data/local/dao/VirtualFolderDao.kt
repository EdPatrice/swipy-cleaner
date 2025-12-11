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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(folder: VirtualFolder): Long

    @Query("SELECT * FROM virtual_folders WHERE name = :name AND filterType = :filterType AND filterValue = :filterValue LIMIT 1")
    suspend fun getFolder(name: String, filterType: com.example.glassmedia.core.model.FilterType, filterValue: String): VirtualFolder?

    @Update
    suspend fun update(folder: VirtualFolder)
}
