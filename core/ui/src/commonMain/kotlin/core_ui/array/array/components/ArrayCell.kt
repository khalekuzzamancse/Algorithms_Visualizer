package core_ui.array.array.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

/**
 * Represent the cell state of an array
 */
data class ArrayCell(
    val position: Offset = Offset.Zero,
    val elementId: Int? = null,
    val color: Color = Color.Unspecified,
)