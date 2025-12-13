package com.example.glassmedia.mediascanner.repository

import com.example.glassmedia.core.model.FilterType
import com.example.glassmedia.data.local.dao.MediaDao
import com.example.glassmedia.data.local.dao.VirtualFolderDao
import com.example.glassmedia.data.local.entity.VirtualFolder
import com.example.glassmedia.mediascanner.domain.MediaScanner
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScanRepository @Inject constructor(
    private val mediaScanner: MediaScanner,
    private val mediaDao: MediaDao,
    private val virtualFolderDao: VirtualFolderDao
) {
    suspend fun syncMedia() {
        // Ensure default virtual folder exists
        val defaultFolder = VirtualFolder(
            id = 1L,
            name = "All Media",
            filterType = FilterType.MIME_FILTER, // Placeholder type
            filterValue = "*/*",
            createdAt = System.currentTimeMillis()
        )
        virtualFolderDao.insert(defaultFolder)

        val scannedItems = mediaScanner.scanMedia()
        // Simple strategy: Insert all. The DAO's OnConflictStrategy.IGNORE will handle duplicates.
        mediaDao.insertAll(scannedItems)
    }
}
