package com.example.glassmedia.data.repository

import com.example.glassmedia.data.local.dao.VirtualFolderDao
import com.example.glassmedia.data.local.entity.VirtualFolder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VirtualFolderRepositoryImpl @Inject constructor(
    private val virtualFolderDao: VirtualFolderDao
) : VirtualFolderRepository {
    override fun getAllFolders(): Flow<List<VirtualFolder>> {
        return virtualFolderDao.getAll()
    }

    override suspend fun createOrUpdateFolder(folder: VirtualFolder): Long {
        return virtualFolderDao.insert(folder)
    }

    override suspend fun getFolder(
        name: String,
        filterType: com.example.glassmedia.core.model.FilterType,
        filterValue: String
    ): VirtualFolder? {
        return virtualFolderDao.getFolder(name, filterType, filterValue)
    }
}
