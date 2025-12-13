package com.example.glassmedia.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

enum class SwipeDirection {
    LEFT, RIGHT, DOWN, UP, NONE
}

@Composable
fun SwipeableCard(
    modifier: Modifier = Modifier,
    onSwipe: (SwipeDirection) -> Unit,
    content: @Composable () -> Unit
) {
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .graphicsLayer {
                rotationZ = offsetX.value / 20f // Add some rotation
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        scope.launch {
                            val x = offsetX.value
                            val y = offsetY.value
                            val threshold = 300f

                            when {
                                x > threshold -> {
                                    // Swipe Right (Keep)
                                    offsetX.animateTo(1000f)
                                    onSwipe(SwipeDirection.RIGHT)
                                }
                                x < -threshold -> {
                                    // Swipe Left (Delete)
                                    offsetX.animateTo(-1000f)
                                    onSwipe(SwipeDirection.LEFT)
                                }
                                y > threshold -> {
                                    // Swipe Down (Skip)
                                    offsetY.animateTo(1000f)
                                    onSwipe(SwipeDirection.DOWN)
                                }
                                else -> {
                                    // Reset
                                    launch { offsetX.animateTo(0f) }
                                    launch { offsetY.animateTo(0f) }
                                }
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                            offsetY.snapTo(offsetY.value + dragAmount.y)
                        }
                    }
                )
            }
    ) {
        content()

        // Visual feedback overlay
        val x = offsetX.value
        val y = offsetY.value
        val threshold = 300f

        val alpha = (kotlin.math.max(abs(x), abs(y)) / threshold).coerceIn(0f, 0.5f)
        val color = when {
            x > 50 -> Color.Green // Keep
            x < -50 -> Color.Red // Delete
            y > 50 -> Color.Yellow // Skip
            else -> Color.Transparent
        }

        if (color != Color.Transparent) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color.copy(alpha = alpha))
            )
        }
    }
}
