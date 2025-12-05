package com.example.glassmedia.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.glassmedia.data.local.entity.TrashItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TrashDao {
    @Query("SELECT * FROM trash_items ORDER BY deletedAt DESC")
    fun getAll(): Flow<List<TrashItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TrashItem)

    @Delete
    suspend fun delete(item: TrashItem)

    @Query("SELECT * FROM trash_items WHERE expiryAt < :timestamp")
    suspend fun getExpiredItems(timestamp: Long): List<TrashItem>
}
