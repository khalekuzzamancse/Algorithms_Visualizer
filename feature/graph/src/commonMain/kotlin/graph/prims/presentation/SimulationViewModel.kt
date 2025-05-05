@file:Suppress("functionName")

package graph.prims.presentation

import core_ui.GlobalColors
import core_ui.graph.common.model.GraphResult
import graph.DiContainer
import graph._core.presentation.BaseRouteController
import graph.prims.domain.model.DijstraSimulationState
import graph.prims.domain.service.DijkstraSimulator
import kotlinx.coroutines.flow.update

internal class SimulationViewModel : BaseRouteController() {
    private lateinit var simulator: DijkstraSimulator

    override fun onGraphCreated(result: GraphResult) {
        super.onGraphCreated(result)
        simulator = DiContainer.createPrimsSimulator(_createGraph())
    }

    override fun onNext() {
        val state = simulator.next()
        _code.update { state.code }
        when (state) {
            is DijstraSimulationState.ProcessingNode -> {
                changeNodeColor(state.node.id)
                blinkNode(state.node.id)
            }
            is DijstraSimulationState.ProcessingEdge -> changeEdgeColor(state.edge.id)
            is DijstraSimulationState.Finished -> handleSimulationFinished()
            else -> Unit // No action for other states
        }
    }


     override fun handleSimulationFinished() {
         super.handleSimulationFinished()
        graphController.filterEdgeByColor(color = GlobalColors.GraphColor.TRAVERSING_EDGE)
    }


    override fun onReset() {
        super.onReset()
        simulator = DiContainer.createPrimsSimulator(_createGraph())
    }

}