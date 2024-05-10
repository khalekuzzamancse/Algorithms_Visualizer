package bfs.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import bfs.di_containter.BFSFactory
import bfs.domain.AlgorithmicEdge
import bfs.domain.AlgorithmicGraph
import bfs.domain.AlgorithmicNode
import graph_editor.domain.Edge
import graph_editor.domain.Graph
import graph_editor.domain.GraphResult
import graph_editor.domain.Node
import graph_editor.domain.VisualEdgeModel
import graph_editor.domain.VisualNodeModel
import graph_editor.ui.GraphEditor
import graphviewer.di_container.GraphViewerFactory
import graphviewer.domain.GraphViewerEdgeModel
import graphviewer.domain.GraphViewerNodeModel
import graphviewer.ui.viewer.GraphViewer
import graphviewer.domain.GraphViewerController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InputGraph() {
    var controller: GraphViewerController? by remember { mutableStateOf(null) }
    var isInputMode by remember { mutableStateOf(true) }
    if (isInputMode) {
        GraphEditor { result ->
            isInputMode = false
            val graph = result.visualGraph
            val nodes = graph.nodes.map { it._toVisualNode() }.toSet()
            val edges = graph.edges.map { it._toVisualEdge() }.toSet()
            //updating the graph viewer controller
            controller = GraphViewerFactory.createGraphViewerController(nodes, edges)
            BFSFactory.setGraph(result.graph._toAlgorithmicGraph())
        }
    }
    //after inputting graph,pass to viewer to view the graph
    val scope = rememberCoroutineScope()
    controller?.let { viewerController ->
        Column {
            Button(onClick = {
                scope.launch {
                    viewerController.nodes.value.forEach {
                        viewerController.changeNodeColor(id = it.id, color = Color.Yellow)
                        delay(2_000)
                    }
                    viewerController.nodes.value.forEach { _ ->
                        delay(2000)
                        viewerController.resetAllNodeColor()
                    }

                }
            }) {
                Text("HighLight")
            }
            GraphViewer(viewerController)
        }

    }


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
    isDirected = false
)

//TODO
@Suppress("FunctionName")
private fun Graph._toAlgorithmicGraph(): AlgorithmicGraph {
    return AlgorithmicGraph(
        isUndirected = isUndirected,
        nodes = nodes.map { it._toAlgorithmicNode() },
        edges = edges.map { it._toAlgorithmicEdge() }
    )
}

@Suppress("FunctionName")
private fun Node._toAlgorithmicNode() = AlgorithmicNode(
    id = id, label = label
)

@Suppress("FunctionName")
private fun Edge._toAlgorithmicEdge() = AlgorithmicEdge(
    from = from._toAlgorithmicNode(),
    to = to._toAlgorithmicNode(),
    cost = cost
)


