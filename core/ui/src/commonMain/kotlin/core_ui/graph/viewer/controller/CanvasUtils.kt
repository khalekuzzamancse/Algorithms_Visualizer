@file:Suppress("unused")
package core_ui.graph.viewer.controller

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import core_ui.graph.common.model.EditorEdgeMode
import core_ui.graph.common.model.EditorNodeModel


internal class CanvasUtils(
    nodes: Set<EditorNodeModel>,
    edges: Set<EditorEdgeMode>
) {
    var nodes = nodes
        private set
    var edges = edges
        private set
    var canvasSize = Size(1000f, 1000f)//initialize with larger size otherwise can causes crash
    private val points = nodes.flatMap { node -> listOf(node.topLeft)
    }+edges.flatMap { node -> listOf(node.start)}+edges.flatMap { node -> listOf(node.end)}+edges.flatMap { node -> listOf(node.control)}
    fun trimExtraSpace(): CanvasUtils {
        val gapFromXAxis = points.minOf { it.x }
        val gapFromYAxis = points.minOf { it.y }
        val gapOffset = Offset(gapFromXAxis, gapFromYAxis)

        nodes = nodes
            .map {
                it.copy(topLeft = it.topLeft - gapOffset)
            }
            .toSet()
        edges = edges
            .map {
                it.copy(
                    start = it.start - gapOffset,
                    control = it.control - gapOffset,
                    end = it.end - gapOffset
                )
            }
            .toSet()
        return this
    }

    fun calculateCanvasSize(): CanvasUtils {
        try {
            // Combine all relevant points (node positions and edge points) in one list
            val points = nodes.flatMap { node -> listOf(node.topLeft) } + edges.flatMap { edge ->
                listOf(edge.start, edge.end, edge.control)
            }
            val nodeSizePx = nodes.first().exactSizePx// can throws exception for empty list
            val maxWidth = points.maxOf { it.x }
            val maxHeight = points.maxOf { it.y } + nodeSizePx
            canvasSize = Size(width = maxWidth, height = maxHeight)
        } catch (_: Exception) {

        }

        return this
    }
}