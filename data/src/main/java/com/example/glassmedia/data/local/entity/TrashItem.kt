package com.example.glassmedia.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trash_items")
data class TrashItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val originalUri: String,
    val trashUri: String,
    val deletedAt: Long,
    val expiryAt: Long
)
