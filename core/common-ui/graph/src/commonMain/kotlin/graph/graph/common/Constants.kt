package graph.graph.common

import androidx.compose.ui.graphics.Color

/**
 * - These color works for both dark and white and need to scope of composable function
 * that is why Defining here
 * - These may be need to use by multiple class such as node editor,edge editor,...etc that is
 * why maintain single place of truth
 * - Maintain the color is important because based on color will identify which edge/node
 * is selected or not
 * - and selection is important state to do some operation such as dragging, removing etc
 */
internal object Constants {
    private val turquoiseBlue = Color(0xFF00B8D4)
    private val sunsetOrange = Color(0xFFFF7043)
    val selectedNodeColor=turquoiseBlue
    val activeNodeColor =sunsetOrange
    val selectedEdgePointColor= turquoiseBlue
    val edgeColor= sunsetOrange
    private val limeGreen = Color(0xFFC0CA33)
    val nodeColor= limeGreen
}