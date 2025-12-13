package com.example.glassmedia.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glassmedia.actions.trash.TrashManager
import com.example.glassmedia.core.model.ReviewedState
import com.example.glassmedia.data.local.dao.MediaDao
import com.example.glassmedia.data.local.dao.SettingsDao
import com.example.glassmedia.data.local.entity.MediaItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaDao: MediaDao,
    private val trashManager: TrashManager,
    private val settingsDao: SettingsDao
) : ViewModel() {

    private val _mediaItems = MutableStateFlow<List<MediaItem>>(emptyList())
    val mediaItems: StateFlow<List<MediaItem>> = _mediaItems.asStateFlow()

    init {
        // Load default folder (ID 1)
        loadMedia(1L)
    }

    fun loadMedia(virtualFolderId: Long) {
        viewModelScope.launch {
            mediaDao.streamByVirtualFolder(virtualFolderId).collect { items ->
                _mediaItems.value = items.filter { it.reviewedState == ReviewedState.UNREVIEWED }
            }
        }
    }

    fun keepMedia(item: MediaItem) {
        viewModelScope.launch {
            mediaDao.update(item.copy(reviewedState = ReviewedState.KEPT, reviewedAt = System.currentTimeMillis()))
        }
    }

    fun trashMedia(item: MediaItem) {
        viewModelScope.launch {
            // Optimistic UI update
            val updatedItem = item.copy(reviewedState = ReviewedState.DELETED, reviewedAt = System.currentTimeMillis())
            mediaDao.update(updatedItem)

            // Perform file operation
            val settings = settingsDao.getSettings().firstOrNull()
            val trashUri = settings?.trashFolderUri?.let { Uri.parse(it) }

            val success = trashManager.moveToTrash(item, trashUri)

            if (!success) {
                // Revert if failed (or handle error UI)
                // For MVP, if move fails, we might want to revert the state to UNREVIEWED
                mediaDao.update(item.copy(reviewedState = ReviewedState.UNREVIEWED, reviewedAt = null))
            }
        }
    }

    fun skipMedia(item: MediaItem) {
        viewModelScope.launch {
            mediaDao.update(item.copy(reviewedState = ReviewedState.SKIPPED, reviewedAt = System.currentTimeMillis()))
        }
    }
}
