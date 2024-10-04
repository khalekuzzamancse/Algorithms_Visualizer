package graphtraversal.presentationlogic.controller

import androidx.compose.ui.graphics.Color
import graph.graph.common.model.GraphResult
import graph.graph.common.model.Node
import graph.graph.viewer.controller.GraphViewerController
import graphtraversal.domain.model.ColorModel
import graphtraversal.domain.model.EdgeModel
import graphtraversal.domain.model.GraphModel
import graphtraversal.domain.model.NodeModel

class GraphManager {
    lateinit var graphController: GraphViewerController
    lateinit var graph: GraphModel

    fun createGraph(result: GraphResult): GraphModel {
        val nodeModels = result.nodes.map { it._toNodeModel() }.toSet()
        val edgeModels = result.edges.map {
            EdgeModel(
                id = it.id,
                u = it.from._toNodeModel(),
                v = it.to._toNodeModel(),
            )
        }.toSet()
        return GraphModel(
            isDirected = result.directed,
            nodes = nodeModels,
            edges = edgeModels,
            source = nodeModels.first()
        ).also {
            graphController = result.controller
            graph = it
        }
    }

    fun resetGraph() {
        graphController.reset()
    }

    fun changeNodeColor(nodeId: String, color: ColorModel) {
        val nodeColor = when (color) {
            ColorModel.White -> Color.White.copy(alpha = 0.8f)
            ColorModel.Gray -> Color.Gray
            ColorModel.Black -> Color.Black
        }
        graphController.changeNodeColor(id = nodeId, color = nodeColor)
    }

    fun changeEdgeColor(edgeId: String, color: Color) {
        graphController.changeEdgeColor(id = edgeId, color = color)
    }

    fun blinkNode(nodeId: String) {
        graphController.blinkNode(nodeId)
    }

    fun stopBlinkAll() {
        graphController.stopBlinkAll()
    }

    private fun Node._toNodeModel() = NodeModel(id = id)
}
