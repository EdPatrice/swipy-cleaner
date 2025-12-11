package com.example.glassmedia.data.repository

import com.example.glassmedia.data.local.entity.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<Settings?>
    suspend fun updateSettings(settings: Settings)
}
