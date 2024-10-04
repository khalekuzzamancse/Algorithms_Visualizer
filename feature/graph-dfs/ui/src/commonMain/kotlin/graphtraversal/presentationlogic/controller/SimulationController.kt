package graphtraversal.presentationlogic.controller

import graphtraversal.domain.model.GraphModel
import graphtraversal.domain.model.SimulationState
import graphtraversal.domain.service.Simulator

class SimulationController(
    private val graphManager: GraphManager
) {
    lateinit var simulator: Simulator

    fun initializeSimulator(graph: GraphModel) {
        simulator = DiContainer.createSimulator(graph)
    }

    fun nextStep() {
        when (val state = simulator.next()) {
//            is SimulationState.ColorChanged -> graphManager.changeNodeColor(state.nodes)
//            is SimulationState.ProcessingEdge -> graphManager.changeEdgeColor(state.id)
//            is SimulationState.NeighborSelection -> handleNeighborSelection(state)
//            is SimulationState.ExecutionAt -> graphManager.blinkNode(state.nodeId)
//            SimulationState.Finished -> graphManager.stopBlinkAll()
//            is SimulationState.BackEdgeDetected -> TODO()
            is SimulationState.BackEdgeDetected -> TODO()
            is SimulationState.ColorChanged -> TODO()
            is SimulationState.ExecutionAt -> TODO()
            SimulationState.Finished -> TODO()
            is SimulationState.NeighborSelection -> TODO()
            is SimulationState.ProcessingEdge -> TODO()
        }
    }

    private fun handleNeighborSelection(state: SimulationState.NeighborSelection) {
        // Handle neighbor selection logic
    }

    fun resetSimulation() {
        simulator = DiContainer.createSimulator(graphManager.graph)
    }
}
