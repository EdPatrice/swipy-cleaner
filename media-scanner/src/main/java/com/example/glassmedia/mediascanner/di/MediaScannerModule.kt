package com.example.glassmedia.mediascanner.di

import com.example.glassmedia.mediascanner.data.MediaScannerImpl
import com.example.glassmedia.mediascanner.domain.MediaScanner
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MediaScannerModule {

    @Binds
    @Singleton
    abstract fun bindMediaScanner(
        mediaScannerImpl: MediaScannerImpl
    ): MediaScanner
}
