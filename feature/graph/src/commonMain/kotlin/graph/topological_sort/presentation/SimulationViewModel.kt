package graph.topological_sort.presentation

import core_ui.GlobalColors
import core_ui.graph.common.model.GraphResult
import graph.DiContainer
import graph._core.presentation.BaseRouteController
import graph.topological_sort.domain.TopologicalSortSimulator
import graph.topological_sort.domain.TopologicalSortState


internal class TopologicalRouteController: BaseRouteController() {
    private lateinit var simulator: TopologicalSortSimulator
    override fun onGraphCreated(result: GraphResult) {
        super.onGraphCreated(result)
        simulator=DiContainer.createTopologicalSimulator(_createGraph())
    }
    override fun onNext() {
      val state=simulator.next()
        consume(state)
    }
    private fun consume(state: TopologicalSortState) {
        when (state) {
            is TopologicalSortState.ColorChanged -> onColorChanged(state.nodeColors)
            is TopologicalSortState.ProcessingEdge -> changeEdgeColor(state.edgeId)
            is TopologicalSortState.ExecutionAt -> handleExecutionAt(state.nodeId)
            is TopologicalSortState.NodeProcessed -> handleNodeProcessed(state.nodeId)
            is TopologicalSortState.StartingNode -> handleStartingNode(state.nodeId)
            is TopologicalSortState.TraversingEdge -> handleTraversingEdge(state.id)
            is TopologicalSortState.TopologicalOrder -> handleTopologicalOrder(state.order)
            is  TopologicalSortState.Finished -> handleSimulationFinished()
            else -> Unit // No action for other states
        }
    }

    override fun onReset() {
      super.onReset()
        simulator=DiContainer.createTopologicalSimulator(_createGraph())
    }


    private fun handleExecutionAt(nodeId: String) {
        graphController.blinkNode(nodeId)
    }

    private fun handleNodeProcessed(nodeId: String) {
        graphController.changeNodeColor(id = nodeId, color = GlobalColors.GraphColor.PROCESSED)
    }

    private fun handleStartingNode(nodeId: String) {
        graphController.blinkNode(nodeId)
    }

    private fun handleTraversingEdge(id: String) {
        graphController.changeEdgeColor(id = id, color = GlobalColors.GraphColor.TRAVERSING_EDGE)

    }

    private fun handleTopologicalOrder(order: List<String>) {
        // Display the topological order to the user
    }


}

