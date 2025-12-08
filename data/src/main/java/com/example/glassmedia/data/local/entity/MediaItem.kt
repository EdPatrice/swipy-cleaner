package com.example.glassmedia.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.glassmedia.core.model.MediaType
import com.example.glassmedia.core.model.ReviewedState

@Entity(tableName = "media_items")
data class MediaItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val uri: String,
    val displayName: String?,
    val mediaType: MediaType,
    val size: Long,
    val dateTaken: Long?,
    val lastModified: Long?,
    val virtualFolderId: Long?,
    val reviewedState: ReviewedState,
    val reviewedAt: Long? = null
)
