package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.controller.ArrayManager
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.visual_array.static_array.ArrayCellElement

@Composable
fun ArrayCellPreview() {
    val cellSize = 64.dp
    val sizePx = with(LocalDensity.current) { cellSize.toPx() }
    val elements = List(5) { index -> "${index+1}"}
    val arrayManager = remember {
        ArrayManager(list = elements, cellSizePx = sizePx)
    }
    Array(cellSize = cellSize, arrayManager = arrayManager)
    CellPointerComposable(
        cellSize=cellSize,
        currentPosition = arrayManager.cells.value[1].position+ Offset(x=0f,y=2*sizePx),
        label = "i"
    )
}

@Composable
internal fun <T> Array(
    cellSize: Dp = 64.dp,
    arrayManager: ArrayManager<T>,
) {

    Column {
        Button(onClick = {
            println(arrayManager.cellsCurrentElements)
        }) {
            Text(text = "State")
        }
        Array(
            modifier = Modifier.fillMaxSize(),
            cellSize = cellSize,
            onCellPositionChanged = arrayManager::onCellPositionChanged,
            state = arrayManager,
            onDragEnd = arrayManager::onDragEnd,
            onDragStart = arrayManager::onDragStart
        )
    }
}

@Composable
private fun <T> Array(
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

@Composable
private fun <T> PlacingElement(
    state: ArrayManager<T>,
    cellSize: Dp,
    onDragStart: (Int) -> Unit,
    onDragEnd: (Int) -> Unit = {},
) {
    //placing the element on top of cells
    state.elements.collectAsState().value.forEachIndexed { index, element ->
        ArrayCellElement(
            size = cellSize,
            currentOffset = element.position,
            onDrag = { dragAmount ->
                state.onDragElement(index, dragAmount)
            }, onDragEnd = {
                onDragEnd(index)
            }, onDragStart = {
                onDragStart(index)
            }
        ) {
            Text(
                text = "${element.value}",
                style = TextStyle(color = Color.White, fontSize = 16.sp),
            )
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun <T> ArrayCells(
    invisibleCell: Boolean = false,
    state: ArrayManager<T>,
    size: Dp,
    onCellPositionChanged: (Int, Offset) -> Unit = { _, _ -> },
) {
    //Placing the cell
    FlowRow {
        state.cells.collectAsState().value.forEachIndexed { index, _ ->
            ArrayCell(size = size,
                hideBorder = invisibleCell,
                onPositionChanged = { position ->
                    onCellPositionChanged(index, position.positionInParent())
                })
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
private fun ArrayCell(
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