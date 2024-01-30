package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.cell.ArrayCells
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.element.PlacingElement
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayManager


@Composable
internal fun <T> Array(
    cellSize: Dp = 64.dp,
    arrayManager: ArrayManager<T>,
) {
    Array(
        modifier = Modifier.wrapContentSize(),
        cellSize = cellSize,
        onCellPositionChanged = arrayManager::onCellPositionChanged,
        state = arrayManager,
        onDragEnd = arrayManager::onDragEnd,
        onDragStart = arrayManager::onDragStart
    )

}

@Composable
internal fun <T> Array(
    modifier: Modifier = Modifier,
    invisibleCell: Boolean = false,
    state: ArrayManager<T>,
    cellSize: Dp,
    onCellPositionChanged: (Int, Offset) -> Unit = { _, _ -> },
    onDragStart: (Int) -> Unit,
    onDragEnd: (Int) -> Unit = {},
) {
    Box(modifier = modifier) {
        ArrayCells(
            invisibleCell = invisibleCell,
            state = state,
            onCellPositionChanged = onCellPositionChanged,
            size = cellSize
        )
        PlacingElement(
            state = state,
            cellSize = cellSize,
            onDragStart = onDragStart,
            onDragEnd = onDragEnd
        )

    }

}


