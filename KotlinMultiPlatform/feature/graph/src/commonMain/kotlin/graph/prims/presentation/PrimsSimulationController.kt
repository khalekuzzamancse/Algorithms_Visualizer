@file:Suppress("functionName")

package graph.prims.presentation

import core.ui.GlobalColors
import core.ui.graph.common.model.GraphResult
import graph.DiContainer
import graph._core.presentation.BaseRouteController
import graph.prims.domain.model.PrimsSimulationState
import graph.prims.domain.service.PrimsSimulator
import kotlinx.coroutines.flow.update

internal class PrimsSimulationController : BaseRouteController() {
    private lateinit var simulator: PrimsSimulator

    override fun onGraphCreated(result: GraphResult) {
        super.onGraphCreated(result)
        simulator = DiContainer.createPrimsSimulator(_createGraph())
    }

    override fun onNext() {
        val state = simulator.next()
        _code.update { state.code }
        when (state) {
            is PrimsSimulationState.ProcessingNode -> {
                changeNodeColor(state.node.id)
                blinkNode(state.node.id)
            }
            is PrimsSimulationState.ProcessingEdge -> changeEdgeColor(state.edge.id)
            is PrimsSimulationState.Finished -> handleSimulationFinished()
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