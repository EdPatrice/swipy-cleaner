package com.example.glassmedia.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.glassmedia.core.model.FilterType

import androidx.room.Index

@Entity(
    tableName = "virtual_folders",
    indices = [Index(value = ["name", "filterType", "filterValue"], unique = true)]
)
data class VirtualFolder(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val filterType: FilterType,
    val filterValue: String,
    val createdAt: Long = System.currentTimeMillis(),
    val lastScannedAt: Long? = null
)
