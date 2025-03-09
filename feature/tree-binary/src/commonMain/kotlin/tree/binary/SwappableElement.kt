package tree.binary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp


@Composable
fun SwappableElement(
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
            style = TextStyle(color = Color.White),
            modifier = Modifier
                .padding(padding)
                .clip(CircleShape)
                .background(color)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}

