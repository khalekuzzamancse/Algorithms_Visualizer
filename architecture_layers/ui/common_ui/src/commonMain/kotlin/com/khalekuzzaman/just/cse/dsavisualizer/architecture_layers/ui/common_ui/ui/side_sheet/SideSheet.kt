package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.side_sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SideSheet(
    modifier: Modifier = Modifier,
    title: String,
    allowNavigation: Boolean = true,
    onDismissRequest: () -> Unit={},
    shape: Shape = RectangleShape,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(containerColor),
    elevation: Dp = 0.dp,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        shadowElevation = elevation
    ) {
        Column(Modifier.width(IntrinsicSize.Max)) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = elevation+2.dp
            ){
                Row(Modifier, verticalAlignment = Alignment.CenterVertically) {
                    AnimatedVisibility (allowNavigation){
                        IconButton(
                            onClick = onDismissRequest
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null
                            )

                        }
                    }
                    Box(Modifier){
                        Text(text = title)
                    }
                }
            }


            content()
        }
    }

}

@Composable
fun SideSheet(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(containerColor),
    elevation: Dp = 0.dp,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        shadowElevation = elevation
    ) {
        Box(Modifier) {
            content()
        }
    }

}