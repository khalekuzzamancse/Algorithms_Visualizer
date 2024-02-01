package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.pseudocode

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

data class PseudoCodeLine(
    val line: String,
    val highLighting: Boolean = false,
    val lineNumber: Int,
)
@Composable
fun PseudoCodeExecutor(modifier: Modifier=Modifier,code: List<PseudoCodeLine>) {
    Column(modifier) {
        code.forEach {
            Text(
                text = it.line,
                color = if (it.highLighting) Color.Red else Color.Unspecified
            )
        }

    }

}