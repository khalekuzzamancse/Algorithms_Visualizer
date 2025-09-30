package graph._core.presentation
import core.ui.graph.common.model.GraphResult
import graph.DiContainer
import graph._core.domain.GraphModel
import graph._core.domain.TraversalSimulationState
import graph._core.domain.TraversalSimulator
import kotlinx.coroutines.flow.update

internal abstract  class TraversalRouteController : BaseRouteController(){
    abstract  fun createSimulator(graph: GraphModel):TraversalSimulator
    private lateinit  var traversalSimulator:TraversalSimulator
    override fun onGraphCreated(result: GraphResult) {
        super.onGraphCreated(result)
        traversalSimulator = DiContainer.createBFSSimulator(_createGraph())
    }
    override fun onNext() {
        val state = traversalSimulator.next()
        consume(state)
    }
    private fun consume(state: TraversalSimulationState) {
        _code.update { state.code }
        when (state) {
            is TraversalSimulationState.ColorChanged -> onColorChanged(state.nodes)
            is TraversalSimulationState.ProcessingEdge -> changeEdgeColor(state.id)
            is TraversalSimulationState.NeighborSelection -> {
                val neighbourIds: List<String> = state.unvisitedNeighbors
                val neighbours = graphController.getNodesById(neighbourIds)
                    .map { UiNodeModel(it.first, it.second) }
                    .toSet()
                state.callback(neighbourIds.first())
            }

            is TraversalSimulationState.ExecutionAt -> {
                blinkNode(state.nodeId)
            }
            is TraversalSimulationState.Finished -> handleSimulationFinished()
            else -> Unit // No action for other states
        }
    }

    override fun onReset() {
        super.onReset()
        traversalSimulator = DiContainer.createBFSSimulator(_createGraph())
    }


}