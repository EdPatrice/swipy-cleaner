package com.example.glassmedia.data.repository

import com.example.glassmedia.data.local.entity.TrashItem

interface TrashRepository {
    suspend fun moveToTrash(item: TrashItem)
    suspend fun deletePermanently(item: TrashItem)
    suspend fun getExpiredItems(timestamp: Long): List<TrashItem>
    suspend fun getAllTrashItems(): List<TrashItem>
}
