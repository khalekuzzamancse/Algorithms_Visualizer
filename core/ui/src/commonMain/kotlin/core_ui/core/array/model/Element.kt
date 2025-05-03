package core_ui.core.array.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

/**
 * Represent the element of array
 * @property position is the topLeft coordinate
 */
data class Element(
    val position: Offset = Offset.Zero,
    val color: Color,
    val label: String,
) {
    override fun toString() = " ( $label:${position.x},${position.y} )"
}