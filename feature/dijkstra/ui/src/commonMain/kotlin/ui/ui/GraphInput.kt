package ui.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import domain.model.DijkstraGraphModel
import domain.model.EdgeModel
import domain.model.NodeModel
import graph_editor.domain.Edge
import graph_editor.domain.Graph
import graph_editor.domain.Node
import graph_editor.domain.VisualEdgeModel
import graph_editor.domain.VisualNodeModel
import graph_editor.ui.GraphEditor
import graphviewer.di_container.GraphViewerFactory
import graphviewer.domain.GraphViewerController
import graphviewer.domain.GraphViewerEdgeModel
import graphviewer.domain.GraphViewerNodeModel

/*

 */

@Composable
internal fun GraphInput(
    onGraphInput: (GraphViewerController) -> Unit,
    canvasSize: (Size) -> Unit,
    onGraphReady: (DijkstraGraphModel) -> Unit,
) {

    var isInputMode by remember { mutableStateOf(true) }

    if (isInputMode) {
        GraphEditor { result ->
            isInputMode = false
            val graph = result.visualGraph
            val nodes = graph.nodes.map { it._toVisualNode() }.toSet()
            val edges = graph.edges.map { it._toVisualEdge() }.toSet()
            //updating the graph viewer controller
            val controller = GraphViewerFactory.createGraphViewerController(nodes, edges)
            canvasSize(graph.calculateCanvasSize())
            onGraphInput(controller)
//            //updating the graph viewer controller
//            val controller = GraphViewerFactory.createGraphViewerController(nodes, edges)
//          //  BFSFactory.setGraph(result.graph._toAlgorithmicGraph())
            canvasSize(graph.calculateCanvasSize())
            onGraphInput(controller)
            onGraphReady(_createDijkstraGraph(result.graph))

        }
    }
    //after inputting graph,pass to viewer to view the graph


}

private fun _createDijkstraGraph(graph: Graph): DijkstraGraphModel {
    val nodeModels = graph.nodes.map { it._toNodeModel() }.toSet()
    val edgeModels = graph.edges.map {
        EdgeModel(
            id = it.id,
            u = it.from._toNodeModel(),
            v = it.to._toNodeModel(),
            cost = it.cost?.toInt() ?: 1
        )
    }.toSet()
    return DijkstraGraphModel(nodeModels, edgeModels, nodeModels.first())
}

@Suppress("FunctionName")
private fun VisualNodeModel._toVisualNode() = GraphViewerNodeModel(
    id = id,
    label = label,
    topLeft = topLeft,
    exactSizePx = sizePx
)

@Suppress("FunctionName")
private fun VisualEdgeModel._toVisualEdge() = GraphViewerEdgeModel(
    id = id,
    start = start,
    end = end,
    control = control,
    cost = cost,
    isDirected = true
)

@Suppress("FunctionName")
private fun Node._toNodeModel() = NodeModel(
    id = id, label = label
)

@Suppress("FunctionName")
private fun Edge._toAlgorithmicEdge() = EdgeModel(
    u = from._toNodeModel(),
    v = to._toNodeModel(),
    cost = cost?.toInt() ?: 1
)


