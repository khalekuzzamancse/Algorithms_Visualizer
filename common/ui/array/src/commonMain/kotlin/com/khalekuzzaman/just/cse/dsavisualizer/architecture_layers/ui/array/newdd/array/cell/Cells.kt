package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.cell

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController


@OptIn(ExperimentalLayoutApi::class)
@Composable
 fun <T> ArrayCells(
    invisibleCell: Boolean = false,
    state: ArrayController<T>,
    size: Dp,
    onCellPositionChanged: (Int, Offset) -> Unit = { _, _ -> },
) {
    //Placing the cell
    FlowRow {
        state.cells.collectAsState().value.forEachIndexed { index, cell ->
            ArrayCell(size = size,
                hideBorder = invisibleCell,
                onPositionChanged = { position ->
                    onCellPositionChanged(index, position.positionInParent())
                },
                backgroundColor = cell.color
            )
        }
    }
}

/**
 * This just show for the border,it can not moved.the array element will be represent by another
 * composable so that they can drag and visible to the user that element changes their position
 * that is why we do not allow the put the array element directly to the cell,instead the element will be
 * placed by using it cell coordinates

 */
@Composable
 fun ArrayCell(
    modifier: Modifier = Modifier,
    size: Dp,
    backgroundColor: Color = Color.Unspecified,
    hideBorder: Boolean = false,
    onPositionChanged: (LayoutCoordinates) -> Unit,
) {
    Box(
        modifier = modifier
            .size(size)
            .border(width = if (hideBorder) 0.dp else 1.dp, color = Color.Black)
            .background(backgroundColor)
            .onGloballyPositioned { position ->
                onPositionChanged(position)
            }
    )

}