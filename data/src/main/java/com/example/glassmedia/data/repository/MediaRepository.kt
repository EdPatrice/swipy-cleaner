package com.example.glassmedia.data.repository

import com.example.glassmedia.data.local.entity.MediaItem
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun getMediaByVirtualFolder(virtualFolderId: Long): Flow<List<MediaItem>>
    suspend fun getMediaItemByUri(uri: String): MediaItem?
    suspend fun insertMediaItems(items: List<MediaItem>)
    suspend fun updateMediaItem(item: MediaItem)
}
