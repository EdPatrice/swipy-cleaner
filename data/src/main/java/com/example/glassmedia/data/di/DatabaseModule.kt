package com.example.glassmedia.data.di

import android.content.Context
import androidx.room.Room
import com.example.glassmedia.data.local.AppDatabase
import com.example.glassmedia.data.local.dao.MediaDao
import com.example.glassmedia.data.local.dao.SettingsDao
import com.example.glassmedia.data.local.dao.TrashDao
import com.example.glassmedia.data.local.dao.VirtualFolderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "glass_media.db"
        ).build()
    }

    @Provides
    fun provideMediaDao(database: AppDatabase): MediaDao = database.mediaDao()

    @Provides
    fun provideVirtualFolderDao(database: AppDatabase): VirtualFolderDao = database.virtualFolderDao()

    @Provides
    fun provideTrashDao(database: AppDatabase): TrashDao = database.trashDao()

    @Provides
    fun provideSettingsDao(database: AppDatabase): SettingsDao = database.settingsDao()
}
