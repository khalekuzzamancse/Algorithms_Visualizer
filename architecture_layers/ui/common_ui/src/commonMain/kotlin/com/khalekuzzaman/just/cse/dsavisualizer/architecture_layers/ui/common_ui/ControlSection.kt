package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NextPlan
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.CodeOff
import androidx.compose.material.icons.filled.NextPlan
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalLayoutApi::class)
@Composable
 fun ControlSection(
    modifier: Modifier=Modifier,
    isCodeOff: Boolean = false,
    onNext: () -> Unit = {},
    onCodeVisibilityToggleRequest: () -> Unit ={},
) {
    FlowRow (modifier){
        IconButton(
            onClick = onNext
        ) {
            Icon(Icons.AutoMirrored.Filled.NextPlan, null)
        }
        IconButton(
            onClick =onCodeVisibilityToggleRequest
        ) {
            Icon(
                imageVector = if (isCodeOff) Icons.Default.CodeOff else Icons.Default.Code,
                contentDescription = "code on off"
            )
        }


    }


}