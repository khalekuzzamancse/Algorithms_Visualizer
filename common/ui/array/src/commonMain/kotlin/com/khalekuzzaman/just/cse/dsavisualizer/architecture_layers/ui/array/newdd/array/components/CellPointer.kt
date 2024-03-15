package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.components

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset

/**
 * Represent the pointer  of the cell ,this helpful when you
 */
@Composable
fun CellPointerComposable(
    cellSize: Dp,
    label: String,
    icon: ImageVector = Icons.Default.KeyboardArrowUp,
    currentPosition: Offset = Offset.Zero,
) {
    val offsetAnimation by animateOffsetAsState(currentPosition, label = "")
    Column(
        modifier = Modifier
            .size(cellSize)
            .offset {
                IntOffset(offsetAnimation.x.toInt(), offsetAnimation.y.toInt())
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Text(
            text = label,
            modifier = Modifier
        )
    }
}
@Composable
fun CellPointerComposable(
    cellSize: Dp,
    label: String,
    position: Offset = Offset.Zero,
) {
    val offsetAnimation by animateOffsetAsState(position, label = "")
    Box(
        modifier = Modifier
            .size(cellSize)
            .offset {
                IntOffset(offsetAnimation.x.toInt(), offsetAnimation.y.toInt())
            }.background(MaterialTheme.colorScheme.tertiary.copy(alpha =0.5f)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = label,
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
}
