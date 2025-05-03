package core_ui.array.array.components

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
import core_ui.array.array.controller.ArrayController
import core_ui.array.swappable.SwappableArrayController


/**
 * The cells of the array,the cell does not direcly contain the element because
 * we want the element be move able or draggable so that in case of sorting algo they can be swapped.
 * that is why we the cell does not contain a fix element
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
 fun <T> ArrayCells(
    invisibleCell: Boolean = false,
    controller: ArrayController<T>,
    size: Dp,
    onCellPositionChanged: (Int, Offset) -> Unit = { _, _ -> },
) {
    //Placing the cell
    FlowRow {
        controller.cells.collectAsState().value.forEachIndexed { index, cell ->
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
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> ArrayCells(
    invisibleCell: Boolean = false,
    controller: SwappableArrayController<T>,
    size: Dp,
    onCellPositionChanged: (Int, Offset) -> Unit = { _, _ -> },
) {
    //Placing the cell
    FlowRow {
        controller.cells.collectAsState().value.forEachIndexed { index, cell ->
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