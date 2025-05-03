package core_ui.core.array

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import core_ui.core.array.controller.VisualArrayController


@Composable
fun VisualArray(
    modifier: Modifier = Modifier,
    controller: VisualArrayController
) {
    _ArrayCells(
        modifier=modifier,
        controller = controller
    )

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun _ArrayCells(
    modifier: Modifier = Modifier,
    controller: VisualArrayController
) {

    val cells = controller.cells.collectAsState().value
    val elements = controller.elements.collectAsState().value
    val cellSize= remember { 64.dp }

    Box(modifier = modifier) {
        FlowRow {
            cells.forEachIndexed { index, cell->
                _Cell(
                    color = cell.color,
                    size = cellSize,
                    onPositionChanged = { position ->
                        controller.onCellPositionChanged(index, position.positionInParent())
                    },
                )
            }
        }

        elements.forEach { element ->
            _Element(
                label = element.label,
                position = element.position,
                color = element.color,
                cellSize = cellSize
            )
        }
        controller.pointers.collectAsState().value.forEach { pointer ->
                if (pointer.position != null)
                    _CellPointer(label = pointer.label, position = pointer.position, cellSize = cellSize)
            }

    }

}



@Composable
private fun _CellPointer(
    cellSize: Dp,
    label: String,
    position: Offset,
) {
    val offsetAnimation by animateOffsetAsState(position, label = "")
    Box(
        modifier = Modifier
            .size(cellSize)
            .offset {
                IntOffset(offsetAnimation.x.toInt(), offsetAnimation.y.toInt())
            }.background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = label,
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}

@Composable
private fun _Element(
    label: String,
    cellSize: Dp,
    color: Color,
    position: Offset,
) {
    val backgroundColor= if (color==Color.Unspecified) MaterialTheme.colorScheme.tertiary else color
    val offsetAnimation by animateOffsetAsState(
        targetValue = position, label = label,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        )
    )

    Box(
        modifier = Modifier
            .offset {
                IntOffset(offsetAnimation.x.toInt(), offsetAnimation.y.toInt())
            }
            .size(cellSize)
            .padding(2.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = MaterialTheme.colorScheme.contentColorFor(backgroundColor))
    }
}

@Composable
private fun _Cell(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color = Color.Unspecified,
    hideBorder: Boolean = false,
    onPositionChanged: (LayoutCoordinates) -> Unit,
) {
    Box(
        modifier = modifier
            .size(size)
            .border(width = if (hideBorder) 0.dp else 1.dp, color = Color.Black)
            .background(color)
            .onGloballyPositioned { position ->
                onPositionChanged(position)
            }
    )

}