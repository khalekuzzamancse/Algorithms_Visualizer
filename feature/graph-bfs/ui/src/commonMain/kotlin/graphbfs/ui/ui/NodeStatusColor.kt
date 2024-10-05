package graphbfs.ui.ui

import androidx.compose.ui.graphics.Color

/**
 * - This model depends on framework or library such as androidx that is why not defining
 * in the `presentationlogic` layer
 */
data class NodeStatusColor(
    /**ui represent of [graphbfs.domain.model.ColorModel.White] color*/
    val undiscovered: Color,
    /**ui represent of [graphbfs.domain.model.ColorModel.Gray] color*/
    val discovered: Color,
    /**ui represent the [graphbfs.domain.model.ColorModel.Black] color*/
    val processed: Color
)