package com.just.cse.digital_diary.two_zero_two_three.common_ui.bottom_sheet.handler

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloseFullscreen
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetControllerIcon(
    modifier: Modifier=Modifier,
    handler: BottomSheetHandler
) {
    Icon(
        modifier = modifier.clickable {
            handler.toggleState()
        },
        imageVector = when (handler.sheetState.value.currentValue) {
            SheetValue.Hidden -> Icons.Default.OpenInFull
            SheetValue.PartiallyExpanded -> Icons.Default.OpenInFull
            SheetValue.Expanded -> Icons.Default.CloseFullscreen
        },
        contentDescription = null
    )

}