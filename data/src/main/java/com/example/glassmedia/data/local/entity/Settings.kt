package com.example.glassmedia.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.glassmedia.core.model.FilterType

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey val id: Int = 1, // Singleton
    val trashRetentionDays: Int = 30,
    val movedFilesTreatedAsReviewed: Boolean = true,
    val virtualFolderDefaultSort: String = "CHRONOLOGICAL",
    val trashFolderUri: String? = null
)
