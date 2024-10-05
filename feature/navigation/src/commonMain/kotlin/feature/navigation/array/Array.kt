import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import feature.navigation.array.controller.ControllerImpl
import kotlinx.coroutines.launch

@Composable
fun ArrayDemo() {
    val controller = remember { ControllerImpl(listOf("10", "20", "30", "40")) }
    var cnt by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    Column {
        Row {
            Button(
                onClick = {
                    scope.launch {
                        controller.swap(0, 3)
                    }
                }
            ) {
                Text("Swap")
            }
            Button(
                onClick = {
                    controller.movePointer((cnt++) % 4)
                }
            ) {
                Text("Move")
            }
        }

        _ArrayCells(controller)
    }


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun _ArrayCells(controller: ControllerImpl) {

    val cells = controller.cells.collectAsState().value
    val allCellPlaced = controller.allCellPlaced.collectAsState().value
    val elements = controller.elements.collectAsState().value

    Box {
        FlowRow {
            cells.forEachIndexed { index, cell ->
                _Cell(
                    onPositionChanged = { position ->
                        controller.onCellPositionChanged(index, position.positionInParent())
                    },
                )
            }
        }

        elements.forEach { element ->
            _Element(
                label = element.label,
                position = element.position
            )
        }
        controller.pointerPosition.collectAsState().value?.let { position ->
            _CellPointer(position = position)
        }

    }


}

@Composable
private fun _CellPointer(
    cellSize: Dp = 64.dp,
    label: String = "i",
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
    size: Dp = 64.dp,
    color: Color = MaterialTheme.colorScheme.tertiary,
    position: Offset,
) {
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
            .size(size)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(label)
    }
}

@Composable
private fun _Cell(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
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