@file:Suppress("functionName")

package core.ui.graph

import androidx.compose.ui.geometry.Offset
import core.ui.graph.common.model.EditorEdgeModel
import core.ui.graph.common.model.EditorNodeModel
import core.ui.graph.editor.controller.GraphEditorController
import core.ui.graph.editor.factory.GraphEditorControllerImpl
import core.ui.graph.editor.factory.GraphProvider
import core.ui.graph.editor.factory.InputControllerImpl
import core.ui.graph.editor.factory.SavedGraphProvider
import core.ui.graph.viewer.controller.GraphViewerController
import core.ui.graph.viewer.controller.GraphViewerControllerImpl


object GraphFactory {

    internal fun createGraphViewerController(
        nodes: Set<EditorNodeModel>,
        edges: Set<EditorEdgeModel>
    ): GraphViewerController = GraphViewerControllerImpl(nodes, edges)

    internal fun createGraphEditorController(
        density: Float,
        initialGraph: Pair<List<EditorNodeModel>, List<EditorEdgeModel>> = Pair(
            emptyList(),
            emptyList()
        ),
    ): GraphEditorController =
        GraphEditorControllerImpl(
            density = density,
            initialGraph = initialGraph,
            inputController = InputControllerImpl(
                drawNodeObserver = { _, _ -> },
                addEdgeRequestObserver = {},
                graphTypeObserver = {}
            )
        )

    fun getDemoGraph(density: Float) = GraphProvider(density = density).getDemoGraph().trimGraphExtraSpace()
    fun getMSTDemoGraph() = SavedGraphProvider.getMSTGraph().trimGraphExtraSpace()
    fun getTopologicalSortDemoGraph() = SavedGraphProvider.getTopologicalSortDemoGraph()

}

fun Pair<List<EditorNodeModel>, List<EditorEdgeModel>> .trimGraphExtraSpace(
): Pair<List<EditorNodeModel>, List<EditorEdgeModel>> {
    val graph=this
    val (nodes, edges) = graph

    // Gather all relevant points
    val allPoints = nodes.map { it.topLeft } +
            edges.flatMap { listOf(it.start, it.end, it.control) }

    // If there are no points, just return as is
    if (allPoints.isEmpty()) return graph

    // Find the smallest x and y (the unnecessary gap)
    val minX = allPoints.minOf { it.x }
    val minY = allPoints.minOf { it.y }
    val gapOffset = Offset(minX, minY)

    // Shift all coordinates by subtracting the gap
    val shiftedNodes = nodes.map { it.copy(topLeft = it.topLeft - gapOffset) }
    val shiftedEdges = edges.map {
        it.copy(
            start = it.start - gapOffset,
            control = it.control - gapOffset,
            end = it.end - gapOffset
        )
    }

    return shiftedNodes to shiftedEdges
}
fun Pair<List<EditorNodeModel>, List<EditorEdgeModel>>.scaleGraph(
    newSizePx: Float
): Pair<List<EditorNodeModel>, List<EditorEdgeModel>> {
    val graph=this
    val (nodes, edges) = graph
    if (nodes.isEmpty()) return graph

    val oldSizePx = nodes.first().exactSizePx
    if (oldSizePx == 0f) return graph // avoid div by zero

    val scale = newSizePx / oldSizePx

    val scaledNodes = nodes.map { node ->
        node.copy(
            topLeft = Offset(node.topLeft.x * scale, node.topLeft.y * scale),
            exactSizePx = newSizePx
        )
    }

    val scaledEdges = edges.map { edge ->
        edge.copy(
            start = Offset(edge.start.x * scale, edge.start.y * scale),
            control = Offset(edge.control.x * scale, edge.control.y * scale),
            end = Offset(edge.end.x * scale, edge.end.y * scale)
        )
    }

    return scaledNodes to scaledEdges
}
