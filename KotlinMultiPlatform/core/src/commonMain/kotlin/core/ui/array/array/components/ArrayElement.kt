package core.ui.array.array.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

/**
 * represent the element of the array
 * @param value toString() of value will be used as label
 */
data class ArrayElement<T>(
    val elementId:Int?=null,
    val position: Offset = Offset.Zero,
    val color: Color = Color.Unspecified,
    val value: T,
)
