package com.example.glassmedia.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.glassmedia.data.local.entity.MediaItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {
    @Query("SELECT * FROM media_items WHERE virtualFolderId = :vfId ORDER BY dateTaken DESC")
    fun streamByVirtualFolder(vfId: Long): Flow<List<MediaItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(items: List<MediaItem>)

    @Update
    suspend fun update(item: MediaItem)

    @Query("SELECT * FROM media_items WHERE uri = :uri LIMIT 1")
    suspend fun getMediaItemByUri(uri: String): MediaItem?
}
