package feature.navigation.array.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

/**
 * Represent the cell state of an array
 * @property position is the topLeft coordinate
 */
data class Cell(
    val index: Int,
    val position: Offset = Offset.Zero,
    val elementId: Int? = null,
    val color: Color = Color.Unspecified,
)