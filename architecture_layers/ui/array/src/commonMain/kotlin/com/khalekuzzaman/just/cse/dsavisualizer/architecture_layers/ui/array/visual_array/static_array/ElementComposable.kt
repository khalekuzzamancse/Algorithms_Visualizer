package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.visual_array.static_array

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun GraphNodeComposablePreview() {
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }
    var position by remember {
        mutableStateOf(Offset.Zero)
    }
    val onDrag: (Offset) -> Unit = { dragAmount ->
        offset += dragAmount
    }
    val onPositionChanged: (LayoutCoordinates) -> Unit = {
        position = it.positionInParent()
    }
    //
    //
    //
    Column(modifier = Modifier.fillMaxSize()) {
        ArrayCellElement(
            size = 64.dp,
            currentOffset = offset,
            onDrag = onDrag,
            onPositionChanged = onPositionChanged
        ) {
            Text(
                text = "01",
                style = TextStyle(color = Color.White, fontSize = 16.sp),
            )
        }
    }


}

@Composable
fun ArrayCellElement(
    size: Dp,
    color: Color = Color.Red,
    currentOffset: Offset = Offset.Zero,
    onDragEnd: () -> Unit = {},
    onDragStart: () -> Unit = {},
    onDrag: (Offset) -> Unit = {},
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
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        onDragStart()
                    },
                    onDrag = { change, dragAmount ->
                        onDrag(dragAmount)
                        change.consume()
                    },
                    onDragEnd = {
                        onDragEnd()
                    }
                )
            }
            .onGloballyPositioned {
                onPositionChanged(it)
            }
            .clickable { onNodeClick() }

    ) {

        Box(Modifier
            .padding(padding)
            .clip(CircleShape)
            .background(color)
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)){
            content()
        }
    }
}