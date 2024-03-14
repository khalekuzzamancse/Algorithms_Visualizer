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
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController


@Composable
fun <T> VisualArray(
    cellSize: Dp = 64.dp,
    enableDrag: Boolean = false,
    arrayController: ArrayController<T>,
) {
    VisualArray(
        modifier = Modifier.wrapContentSize(),
        cellSize = cellSize,
        onCellPositionChanged = arrayController::onCellPositionChanged,
        state = arrayController,
        onDragEnd = {
            if (enableDrag)
                arrayController.onDragEnd(it)
        },
        onDragStart = {
            if (enableDrag)
                arrayController.onDragStart(it)
        }
    )

}

@Composable
private fun <T> VisualArray(
    modifier: Modifier = Modifier,
    invisibleCell: Boolean = false,
    enableDrag: Boolean=false,
    state: ArrayController<T>,
    cellSize: Dp,
    onCellPositionChanged: (Int, Offset) -> Unit = { _, _ -> },
    onDragStart: (Int) -> Unit={},
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
            enableDrag=false,
            state = state,
            cellSize = cellSize,
            onDragStart = onDragStart,
            onDragEnd = onDragEnd
        )

    }

}


