package com.example.glassmedia.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.glassmedia.ui.components.GlassCard
import com.example.glassmedia.ui.components.SwipeDirection
import com.example.glassmedia.ui.components.SwipeableCard
import com.example.glassmedia.ui.viewmodel.MediaViewModel

@Composable
fun MediaStackScreen(
    viewModel: MediaViewModel = hiltViewModel()
) {
    val mediaItems by viewModel.mediaItems.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (mediaItems.isEmpty()) {
            Text(text = "No media to review")
        } else {
            // Stack effect: render reversed so first item is on top
            mediaItems.take(3).reversed().forEachIndexed { index, item ->
                // Only the top card is swipeable
                val isTopCard = index == mediaItems.take(3).size - 1

                if (isTopCard) {
                    SwipeableCard(
                        onSwipe = { direction ->
                            when (direction) {
                                SwipeDirection.RIGHT -> viewModel.keepMedia(item)
                                SwipeDirection.LEFT -> viewModel.trashMedia(item)
                                SwipeDirection.DOWN -> viewModel.skipMedia(item)
                                else -> {}
                            }
                        }
                    ) {
                        MediaCardContent(uri = item.uri)
                    }
                } else {
                    GlassCard(
                        modifier = Modifier
                            .size(300.dp, 500.dp) // Fixed size for stack effect visual
                    ) {
                        MediaCardContent(uri = item.uri)
                    }
                }
            }
        }
    }
}

@Composable
fun MediaCardContent(uri: String) {
    GlassCard(
        modifier = Modifier.size(300.dp, 500.dp)
    ) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
