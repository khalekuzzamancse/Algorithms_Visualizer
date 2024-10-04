package graphtraversal.domain.model

import kotlin.coroutines.Continuation

sealed interface SimulationState {
    data class ColorChanged(val nodes: Set<Pair<NodeModel, ColorModel>>) : SimulationState
    data class ProcessingEdge(val id: String) : SimulationState
    data class NeighborSelection(
        val unvisitedNeighbors: List<String>,
        val callback: (String) -> Unit
    ) : SimulationState

    data class BackEdgeDetected(val edge: EdgeModel) : SimulationState
    data object Finished : SimulationState
    data class ExecutionAt(val nodeId: String) : SimulationState

}