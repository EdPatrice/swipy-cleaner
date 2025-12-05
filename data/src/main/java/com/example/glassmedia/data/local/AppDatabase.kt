package com.example.glassmedia.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.glassmedia.data.local.dao.MediaDao
import com.example.glassmedia.data.local.dao.SettingsDao
import com.example.glassmedia.data.local.dao.TrashDao
import com.example.glassmedia.data.local.dao.VirtualFolderDao
import com.example.glassmedia.data.local.entity.MediaItem
import com.example.glassmedia.data.local.entity.Settings
import com.example.glassmedia.data.local.entity.TrashItem
import com.example.glassmedia.data.local.entity.VirtualFolder

import androidx.room.TypeConverters

@Database(
    entities = [MediaItem::class, VirtualFolder::class, TrashItem::class, Settings::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
    abstract fun virtualFolderDao(): VirtualFolderDao
    abstract fun trashDao(): TrashDao
    abstract fun settingsDao(): SettingsDao
}
