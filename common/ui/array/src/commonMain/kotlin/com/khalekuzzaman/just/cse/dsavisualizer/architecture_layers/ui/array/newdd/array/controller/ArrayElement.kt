package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

/**
 * @param value toString() of value will be used as label
 */
data class ArrayElement<T>(
    val position: Offset = Offset.Zero,
    val color: Color = Color.Unspecified,
    val value: T,
)
