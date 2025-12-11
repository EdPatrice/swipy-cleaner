package com.example.glassmedia.data.repository

import com.example.glassmedia.data.local.dao.TrashDao
import com.example.glassmedia.data.local.entity.TrashItem
import javax.inject.Inject

class TrashRepositoryImpl @Inject constructor(
    private val trashDao: TrashDao
) : TrashRepository {
    override suspend fun moveToTrash(item: TrashItem) {
        trashDao.insert(item)
    }

    override suspend fun deletePermanently(item: TrashItem) {
        trashDao.delete(item)
    }

    override suspend fun getExpiredItems(timestamp: Long): List<TrashItem> {
        return trashDao.getExpiredItems(timestamp)
    }

    override suspend fun getAllTrashItems(): List<TrashItem> {
        return trashDao.getAll()
    }
}
