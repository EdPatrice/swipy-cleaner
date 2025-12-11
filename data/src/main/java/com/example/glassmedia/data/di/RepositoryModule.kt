package com.example.glassmedia.data.di

import com.example.glassmedia.data.repository.MediaRepository
import com.example.glassmedia.data.repository.MediaRepositoryImpl
import com.example.glassmedia.data.repository.SettingsRepository
import com.example.glassmedia.data.repository.SettingsRepositoryImpl
import com.example.glassmedia.data.repository.TrashRepository
import com.example.glassmedia.data.repository.TrashRepositoryImpl
import com.example.glassmedia.data.repository.VirtualFolderRepository
import com.example.glassmedia.data.repository.VirtualFolderRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMediaRepository(
        impl: MediaRepositoryImpl
    ): MediaRepository

    @Binds
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository

    @Binds
    abstract fun bindTrashRepository(
        impl: TrashRepositoryImpl
    ): TrashRepository

    @Binds
    abstract fun bindVirtualFolderRepository(
        impl: VirtualFolderRepositoryImpl
    ): VirtualFolderRepository
}
