package feature.navigation.array.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
/**
 * Represent the element of array
 * @property position is the topLeft coordinate
 */
data class Element(
    val id:Int,
    val position: Offset = Offset.Zero,
    val color: Color = Color.Unspecified,
    val label: String,
)