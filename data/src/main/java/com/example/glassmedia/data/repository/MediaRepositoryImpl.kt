package com.example.glassmedia.data.repository

import com.example.glassmedia.data.local.dao.MediaDao
import com.example.glassmedia.data.local.entity.MediaItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mediaDao: MediaDao
) : MediaRepository {
    override fun getMediaByVirtualFolder(virtualFolderId: Long): Flow<List<MediaItem>> {
        return mediaDao.streamByVirtualFolder(virtualFolderId)
    }

    override suspend fun getMediaItemByUri(uri: String): MediaItem? {
        return mediaDao.getMediaItemByUri(uri)
    }

    override suspend fun insertMediaItems(items: List<MediaItem>) {
        mediaDao.insertAll(items)
    }

    override suspend fun updateMediaItem(item: MediaItem) {
        mediaDao.update(item)
    }
}
