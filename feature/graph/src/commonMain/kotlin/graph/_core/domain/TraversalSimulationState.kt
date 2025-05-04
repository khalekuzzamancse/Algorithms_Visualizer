package graph._core.domain

sealed interface TraversalSimulationState {
    val code:String
    data class Misc(override val code:String): TraversalSimulationState
    data class ColorChanged(val nodes: Set<Pair<NodeModel, ColorModel>>, override val code:String) :
        TraversalSimulationState
    data class ProcessingEdge(val id: String,override val code:String) : TraversalSimulationState
    data class NeighborSelection(
        val unvisitedNeighbors: List<String>,
        val callback: (String) -> Unit,
        override val code:String
    ) : TraversalSimulationState

    data class Finished (override val code:String): TraversalSimulationState
    data class ExecutionAt(val nodeId: String,override val code:String) : TraversalSimulationState

}