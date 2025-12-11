package com.example.glassmedia.mediascanner

import com.example.glassmedia.data.repository.MediaRepository
import com.example.glassmedia.data.repository.VirtualFolderRepository

interface MediaScanner {
    suspend fun scanMedia()
}
