package core_ui.array.swappable

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * It is used to define the cell element,the element are take the same coordinate as it
 * corresponding cell takes,
 * [controller] has the information how many cell it has and what are the cell value needed to be
 * so based on the number of cell and the list value it will create and place UI represent of the element of array
 */
@Composable
fun <T> SwappableElementPlacer(
    controller: SwappableArrayController<T>,
    cellSize: Dp,
) {
    //placing the element on top of cells
    controller.cells.collectAsState().value.forEach{cell ->
        val element=cell.element
        if (element != null) {
            ArrayCellElement(
                size = cellSize,
                currentOffset = element.position,
                color = MaterialTheme.colorScheme.tertiary
            ) {
                Text(
                    text = "${element.value}",
                    style = TextStyle(color = MaterialTheme.colorScheme.onTertiary, fontSize = 16.sp),
                )
            }
        }
    }
}

/**
 * It denote a single element of the array that will take the same coordinate as it corresponding cell
 */
@Composable
private fun ArrayCellElement(
    size: Dp,
    color: Color = Color.Red,
    currentOffset: Offset = Offset.Zero,
    onPositionChanged: (LayoutCoordinates) -> Unit = {},
    onNodeClick: () -> Unit = {},
    content:@Composable ()->Unit = {},
) {
    val offsetAnimation by animateOffsetAsState(currentOffset, label = "")
    val padding = 8.dp
    Box(
        modifier = Modifier
            .size(size)
            .offset {
                IntOffset(offsetAnimation.x.toInt(), offsetAnimation.y.toInt())
            }
            .onGloballyPositioned {
                onPositionChanged(it)
            }
            .clickable { onNodeClick() }

    ) {

        Box(
            Modifier
                .padding(padding)
                .clip(CircleShape)
                .background(color)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)){
            content()
        }
    }
}