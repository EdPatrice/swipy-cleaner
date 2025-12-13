package com.example.glassmedia.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.glassmedia.data.local.entity.TrashItem

@Dao
interface TrashDao {
    @Insert
    suspend fun insert(trashItem: TrashItem)

    @Query("SELECT * FROM trash_items WHERE expiryAt < :now")
    suspend fun getExpiredItems(now: Long): List<TrashItem>

    @Delete
    suspend fun delete(trashItem: TrashItem)

    @Query("SELECT * FROM trash_items")
    suspend fun getAll(): List<TrashItem>
}
