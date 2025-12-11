package com.example.glassmedia.mediascanner.di

import com.example.glassmedia.mediascanner.MediaScanner
import com.example.glassmedia.mediascanner.MediaScannerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MediaScannerModule {

    @Binds
    abstract fun bindMediaScanner(
        impl: MediaScannerImpl
    ): MediaScanner
}
