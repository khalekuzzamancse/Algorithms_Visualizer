package graphbfs.domain.model

sealed interface SimulationState {

    data class ColorChanged(val nodes: Set<Pair<NodeModel, ColorModel>>) : SimulationState
    data class ProcessingEdge(val id: String) : SimulationState
    data class NeighborSelection(
        val unvisitedNeighbors: List<String>,
        val callback: (List<String>) -> Unit
    ) : SimulationState

    data object Finished : SimulationState
    data class ExecutionAt(val nodeId: String) : SimulationState

}