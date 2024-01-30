package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.controls

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

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ControlSection(
    isCodeOff:Boolean=false,
    onNext: ()->Unit={},
    onCodeOffRequest: ()->Unit={},
    onCodeOnRequest: ()->Unit={},
) {
    FlowRow {
        IconButton(
            onClick = onNext
        ) {
            Icon(Icons.AutoMirrored.Filled.NextPlan,null)
        }
        if (isCodeOff){
            IconButton(
                onClick = onCodeOnRequest
            ) {
                Icon(Icons.Filled.Code,null)
            }
        }
        else{
            IconButton(
                onClick = onCodeOffRequest
            ) {
                Icon(Icons.Filled.CodeOff,null)
            }
        }


    }



}