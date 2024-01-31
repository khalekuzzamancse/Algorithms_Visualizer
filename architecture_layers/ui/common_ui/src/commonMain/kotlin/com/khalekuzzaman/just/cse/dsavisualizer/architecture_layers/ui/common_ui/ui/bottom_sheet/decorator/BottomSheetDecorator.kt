package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.bottom_sheet.decorator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDecorator(
    modifier: Modifier = Modifier,
    sheetPeekHeight: Dp = 54.dp,
    sheetShape: Shape = BottomSheetDefaults.ExpandedShape,
    sheetContainerColor: Color = BottomSheetDefaults.ContainerColor,
    sheetContentColor: Color = contentColorFor(sheetContainerColor),
    sheetTonalElevation: Dp = BottomSheetDefaults.Elevation,
    sheetShadowElevation: Dp = 6.dp,
    sheetDragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    sheetSwipeEnabled: Boolean = true,
    topBar: @Composable (() -> Unit)? = null,
    //mandatory parameters
    sheetState: SheetState,
    sheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState,
    )

    BottomSheetScaffold(
        modifier = modifier,
        sheetContainerColor = sheetContainerColor,
        sheetContentColor = sheetContentColor,
        sheetShadowElevation = sheetShadowElevation,
        sheetTonalElevation = sheetTonalElevation,
        sheetPeekHeight = sheetPeekHeight,
        sheetDragHandle = sheetDragHandle,
        sheetSwipeEnabled = sheetSwipeEnabled,
        topBar = topBar,
        sheetShape = sheetShape,
        scaffoldState = scaffoldState,
        sheetContent = { sheetContent() },
        content = {
            Box(Modifier.padding(it)) { content() }
        }
    )
}