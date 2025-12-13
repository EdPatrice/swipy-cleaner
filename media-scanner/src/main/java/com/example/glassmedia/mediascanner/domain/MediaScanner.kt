package com.example.glassmedia.mediascanner.domain

import com.example.glassmedia.data.local.entity.MediaItem
import kotlinx.coroutines.flow.Flow

interface MediaScanner {
    suspend fun scanMedia(): List<MediaItem>
}
