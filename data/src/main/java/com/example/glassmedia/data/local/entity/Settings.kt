package com.example.glassmedia.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.glassmedia.core.model.FilterType

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey val id: Int = 1, // Singleton
    val movedFilesTreatedAsReviewed: Boolean = true,
    val virtualFolderDefaultSort: String = "CHRONOLOGICAL", // Using String for simplicity or could be Enum
    val trashRetentionDays: Int = 30
)
