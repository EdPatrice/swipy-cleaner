package com.example.glassmedia.data.repository

import com.example.glassmedia.data.local.dao.SettingsDao
import com.example.glassmedia.data.local.entity.Settings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDao: SettingsDao
) : SettingsRepository {
    override fun getSettings(): Flow<Settings?> {
        return settingsDao.getSettings()
    }

    override suspend fun updateSettings(settings: Settings) {
        settingsDao.insert(settings)
    }
}
