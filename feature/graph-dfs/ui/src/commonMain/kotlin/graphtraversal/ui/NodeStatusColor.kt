package graphtraversal.ui

import androidx.compose.ui.graphics.Color

/**
 * - This model depends on framework or library such as androidx that is why not defining
 * in the `presentationlogic` layer
 */
data class NodeStatusColor(
    /**ui represent of [graphtraversal.domain.model.ColorModel.White] color*/
    val undiscovered: Color,
    /**ui represent of [graphtraversal.domain.model.ColorModel.Gray] color*/
    val discovered: Color,
    /**ui represent the [graphtraversal.domain.model.ColorModel.Black] color*/
    val processed: Color
)