package graphtopologicalsort.ui

import androidx.compose.ui.graphics.Color

/**
 * - This model depends on framework or library such as androidx that is why not defining
 * in the `presentationlogic` layer
 */
data class StatusColor(
    val nodeUndiscovered: Color,
    val nodeDiscovered: Color,
    val nodeProcessed: Color,

    // Edge status colors
    val edgeTraversing: Color,
    val edgeProcessing: Color
)