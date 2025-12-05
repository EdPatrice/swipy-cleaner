package com.example.glassmedia.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.glassmedia.core.model.FilterType

@Entity(tableName = "virtual_folders")
data class VirtualFolder(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val filterType: FilterType,
    val filterValue: String,
    val createdAt: Long = System.currentTimeMillis(),
    val lastScannedAt: Long? = null
)
