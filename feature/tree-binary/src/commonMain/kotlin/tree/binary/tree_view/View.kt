@file:Suppress("unused","className","functionName")

package tree.binary.tree_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp



@Composable
fun <T : Comparable<T>> TreeView(
    size: Dp = 50.dp,
    controller: TreeViewController<T>
) {
    val density = LocalDensity.current
    val offset = with(density) {
        Offset(size.toPx(), size.toPx()).div(2f)
    }

    BoxWithConstraints(Modifier.padding(20.dp).size(400.dp)) {
        val canvasWidth = constraints.maxWidth.toFloat()
        val canvasHeight = constraints.maxHeight.toFloat()
        controller.onCanvasSizeChanged(canvasWidth, canvasHeight)
        val nodes = controller.nodes.collectAsState().value
        val lines = controller.lines.collectAsState().value

        Box(Modifier.size(400.dp).drawBehind {
            lines.forEach { (start, end) ->
                drawLine(
                    color = Color.Black,
                    start = start,
                    end = end,
                    strokeWidth = 2f
                )
            }
        }) {
            nodes.forEach { node ->
                _VisualNode(
                    label = node.label,
                    size = size,
                    offset = node.center - offset,
                    color = node.color
                )
            }

        }
    }
}


@Composable
private fun _VisualNode(
    label:String,
    size: Dp = 50.dp,
    offset: Offset= Offset.Zero,
    color: Color=Color.Blue
) {
    //val offsetAnimation by animateOffsetAsState(offset, label = "")
    val padding = 8.dp
    Box(
        modifier = Modifier
            .size(size)
            .offset {
                IntOffset(offset.x.toInt(), offset.y.toInt())
            }
    ) {
        Text(
            text = label,
            style = TextStyle(color = if(color.luminance()<0.6f )Color.White else Color.Black),
            modifier = Modifier
                .padding(padding)
                .clip(CircleShape)
                .background(color)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}




