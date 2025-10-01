@file:Suppress("functionName")
package graph.djkstra.presentation

import core.ui.graph.common.model.GraphResult
import core.ui.graph.common.model.Node
import graph.DiContainer
import graph._core.presentation.BaseRouteController
import graph.djkstra.domain.model.DijkstraGraphModel
import graph.djkstra.domain.model.EdgeModel
import graph.djkstra.domain.model.NodeModel
import graph.djkstra.domain.model.NodeModel.Companion.INFINITY
import graph.djkstra.domain.model.SimulationState
import graph.djkstra.domain.service.Simulator
import kotlinx.coroutines.flow.update

internal class DiSimulationContoller (private val color: StatusColor):BaseRouteController(){
    private lateinit var simulator: Simulator
    override fun onGraphCreated(result: GraphResult) {
        super.onGraphCreated(result)
        simulator = DiContainer.createSimulator(createGraph())
    }
    override fun onNext() {
        val state = simulator.next()
        _code.update { state.code }
        when ( state ) {
            is SimulationState.ProcessingNode -> handleProcessingNode(state.node)
            is SimulationState.ProcessingEdge -> handleProcessingEdge(state.edge)
            is SimulationState.DistanceUpdated -> handleDistanceUpdated(state.nodes)
            is SimulationState.Finished -> handleSimulationFinished()
            else -> Unit // No action for other states
        }
    }

    override fun onReset() {
      super.onReset()
        simulator = DiContainer.createSimulator(createGraph())
    }

    private fun handleProcessingNode(node: NodeModel) {
        graphController.changeNodeColor(id = node.id, color = color.processedNode)
        graphController.blinkNode(node.id)
    }


    private fun handleProcessingEdge(edge: EdgeModel) {
        graphController.changeEdgeColor(id = edge.id, color = color.processingEdge)
    }


    private fun handleDistanceUpdated(nodes: Set<NodeModel>) {
        nodes.forEach {
            val distance = if (it.distance == INFINITY) "∞" else it.distance.toString()
            graphController.updateDistance(id = it.id, distance = distance)
        }
    }

    private fun createGraph(): DijkstraGraphModel {
        val nodeModels = result.nodes.map { it._toNodeModel() }.toSet()
        val edgeModels = result.edges.map {
            EdgeModel(
                id = it.id,
                u = it.from._toNodeModel(),
                v = it.to._toNodeModel(),
                cost = it.cost?.toInt() ?: 1
            )
        }.toSet()
        return DijkstraGraphModel(nodeModels, edgeModels, nodeModels.first())
    }
    private fun Node._toNodeModel() = NodeModel(
        id = id, label = label
    )

}