package graphtraversal.domain.model

sealed interface SimulationState {
    val code:String
    data class Misc(override val code:String):SimulationState
    data class ColorChanged(val nodes: Set<Pair<NodeModel, ColorModel>>,override val code:String) : SimulationState
    data class ProcessingEdge(val id: String,override val code:String) : SimulationState
    data class NeighborSelection(
        val unvisitedNeighbors: List<String>,
        val callback: (String) -> Unit,
        override val code:String
    ) : SimulationState

    data class BackEdgeDetected(val edge: EdgeModel,override val code:String) : SimulationState
    data class Finished (override val code:String): SimulationState
    data class ExecutionAt(val nodeId: String,override val code:String) : SimulationState

}