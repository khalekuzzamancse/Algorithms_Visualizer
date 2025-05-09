package core.ui.array.swappable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.ui.array.array.components.ArrayCells


@Composable
fun <T> SwappableVisualArray(
    cellSize: Dp = 64.dp,
    controller: SwappableArrayController<T>,
) {
    VisualArray(
        modifier = Modifier.wrapContentSize(),
        cellSize = cellSize,
        onCellPositionChanged = controller::onCellPositioned,
        state = controller
    )

}

@Composable
private fun <T> VisualArray(
    modifier: Modifier = Modifier,
    invisibleCell: Boolean = false,
    state: SwappableArrayController<T>,
    cellSize: Dp,
    onCellPositionChanged: (Int, Offset) -> Unit = { _, _ -> },
) {
    Box(modifier = modifier) {
        ArrayCells(
            invisibleCell = invisibleCell,
            controller = state,
            onCellPositionChanged = onCellPositionChanged,
            size = cellSize
        )
        SwappableElementPlacer(
            controller = state,
            cellSize = cellSize,
        )

    }

}


