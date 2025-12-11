package com.example.glassmedia.data.repository

import com.example.glassmedia.data.local.entity.VirtualFolder
import kotlinx.coroutines.flow.Flow

import com.example.glassmedia.core.model.FilterType

interface VirtualFolderRepository {
    fun getAllFolders(): Flow<List<VirtualFolder>>
    suspend fun createOrUpdateFolder(folder: VirtualFolder): Long
    suspend fun getFolder(name: String, filterType: FilterType, filterValue: String): VirtualFolder?
}
