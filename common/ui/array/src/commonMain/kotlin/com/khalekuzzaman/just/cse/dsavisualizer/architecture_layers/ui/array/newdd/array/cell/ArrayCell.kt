package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.cell

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class ArrayCell(
    val position: Offset = Offset.Zero,
    val elementRef: Int? = null,
    val color: Color = Color.Unspecified,
)