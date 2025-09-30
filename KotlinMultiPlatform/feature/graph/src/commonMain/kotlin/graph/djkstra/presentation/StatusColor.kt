package graph.djkstra.presentation

import androidx.compose.ui.graphics.Color

/**
 * - This model depends on framework or library such as androidx that is why not defining
 * in the `presentationlogic` layer
 */
data class StatusColor(
    val processingEdge: Color,
    val processedNode: Color
)