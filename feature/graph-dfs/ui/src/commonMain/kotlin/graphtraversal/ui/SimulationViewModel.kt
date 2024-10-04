@file:Suppress("functionName")

package graphtraversal.ui

import DiContainer
import graph.graph.common.model.GraphResult
import graph.graph.common.model.Node
import graph.graph.viewer.controller.GraphViewerController
import graphtraversal.domain.model.EdgeModel
import graphtraversal.domain.model.GraphModel
import graphtraversal.domain.model.NodeModel
import graphtraversal.domain.service.Simulator
import graphtraversal.presentationlogic.controller.AutoPlayerImpl
import graphtraversal.presentationlogic.controller.NeighborSelectorImpl
import graphtraversal.presentationlogic.controller.SimulationStateConsumerImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SimulationViewModel {

    private lateinit var simulator: Simulator
    private lateinit var result: GraphResult
    private lateinit var consumer: SimulationStateConsumerImpl
    private val _isInputMode = MutableStateFlow(true)
    lateinit var graphController: GraphViewerController
    val neighborSelector = NeighborSelectorImpl()
    val autoPlayer = AutoPlayerImpl(::onNext)
    val isInputMode = _isInputMode.asStateFlow()


    fun onGraphCreated(result: GraphResult) {

        graphController = result.controller
        consumer = SimulationStateConsumerImpl(
            graphController = result.controller,
            autoPlayer = autoPlayer,
            neighborSelector = neighborSelector,
            nodes = result.nodes.toList()
        )
        this.result = result
        simulator = DiContainer.createSimulator(_createGraph())
        _isInputMode.update { false }

    }

    fun onReset() {
        graphController=result.controller
        simulator = DiContainer.createSimulator(_createGraph())
        consumer.onReset()
        consumer = SimulationStateConsumerImpl(
            graphController = result.controller,
            autoPlayer = autoPlayer,
            neighborSelector = neighborSelector,
            nodes = result.nodes.toList()
        )

    }

    fun onNext() {
        val state = simulator.next()
        consumer.consume(state)
    }


    private fun _createGraph(): GraphModel {
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
        )
    }

    private fun Node._toNodeModel() = NodeModel(
        id = id,
    )


}