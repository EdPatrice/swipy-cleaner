package com.example.glassmedia.mediascanner

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.glassmedia.core.model.FilterType
import com.example.glassmedia.core.model.MediaType
import com.example.glassmedia.core.model.ReviewedState
import com.example.glassmedia.data.local.entity.MediaItem
import com.example.glassmedia.data.local.entity.VirtualFolder
import com.example.glassmedia.data.repository.MediaRepository
import com.example.glassmedia.data.repository.VirtualFolderRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class MediaScannerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mediaRepository: MediaRepository,
    private val virtualFolderRepository: VirtualFolderRepository
) : MediaScanner {

    private val projection = arrayOf(
        MediaStore.Files.FileColumns._ID,
        MediaStore.Files.FileColumns.DATA, // For path/bucket logic
        MediaStore.Files.FileColumns.DISPLAY_NAME,
        MediaStore.Files.FileColumns.SIZE,
        MediaStore.Files.FileColumns.DATE_TAKEN,
        MediaStore.Files.FileColumns.DATE_MODIFIED,
        MediaStore.Files.FileColumns.MIME_TYPE,
        MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
        MediaStore.Files.FileColumns.BUCKET_ID
    )

    override suspend fun scanMedia() = withContext(Dispatchers.IO) {
        val resolver = context.contentResolver

        // Scan Images
        scanMediaStore(
            resolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            MediaType.IMAGE
        )

        // Scan Videos
        scanMediaStore(
            resolver,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            MediaType.VIDEO
        )
    }

    private suspend fun scanMediaStore(
        resolver: ContentResolver,
        collectionUri: Uri,
        mediaType: MediaType
    ) {
        // Simplified query - get everything
        val cursor = resolver.query(
            collectionUri,
            projection,
            null,
            null,
            "${MediaStore.Files.FileColumns.DATE_TAKEN} DESC"
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val pathColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
            val dateTakenColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_TAKEN)
            val dateModifiedColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED)
            val bucketNameColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)

            // Temporary map to cache virtual folders in memory during scan
            val folderCache = mutableMapOf<String, Long>()

            val batchSize = 100
            val batch = mutableListOf<MediaItem>()

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val path = it.getString(pathColumn)
                val name = it.getString(nameColumn)
                val size = it.getLong(sizeColumn)
                val dateTaken = it.getLong(dateTakenColumn)
                val dateModified = it.getLong(dateModifiedColumn)
                val bucketName = it.getString(bucketNameColumn) ?: "Unknown"

                val contentUri = Uri.withAppendedPath(collectionUri, id.toString()).toString()

                // Logic to associate with Virtual Folder
                // For MVP, we treat each Bucket as a Virtual Folder
                // In a real app we might query existing folders first or have a smarter sync
                val folderId = getOrCreateFolderId(bucketName, folderCache)

                val mediaItem = MediaItem(
                    uri = contentUri,
                    displayName = name,
                    mediaType = mediaType,
                    size = size,
                    dateTaken = if (dateTaken > 0) dateTaken else dateModified * 1000,
                    lastModified = dateModified * 1000,
                    virtualFolderId = folderId,
                    reviewedState = ReviewedState.UNREVIEWED
                )

                batch.add(mediaItem)

                if (batch.size >= batchSize) {
                    mediaRepository.insertMediaItems(batch)
                    batch.clear()
                }
            }

            if (batch.isNotEmpty()) {
                mediaRepository.insertMediaItems(batch)
            }
        }
    }

    private suspend fun getOrCreateFolderId(bucketName: String, cache: MutableMap<String, Long>): Long {
        if (cache.containsKey(bucketName)) {
            return cache[bucketName]!!
        }

        val folder = VirtualFolder(
            name = bucketName,
            filterType = FilterType.BUCKET,
            filterValue = bucketName
        )

        // Try to find existing folder first
        val existing = virtualFolderRepository.getFolder(bucketName, FilterType.BUCKET, bucketName)
        if (existing != null) {
            cache[bucketName] = existing.id
            return existing.id
        }

        // Insert new
        val id = virtualFolderRepository.createOrUpdateFolder(folder)
        if (id != -1L) {
            cache[bucketName] = id
            return id
        } else {
            // Race condition or conflict, try fetching again
            val retry = virtualFolderRepository.getFolder(bucketName, FilterType.BUCKET, bucketName)
            if (retry != null) {
                cache[bucketName] = retry.id
                return retry.id
            }
        }

        return -1L // Should not happen
    }
}
