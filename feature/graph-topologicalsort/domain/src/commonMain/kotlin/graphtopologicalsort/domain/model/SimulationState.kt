package graphtopologicalsort.domain.model

sealed interface SimulationState {
    val code:String
    //    data class ColorChanged(val nodes: Set<Pair<NodeModel, ColorModel>>) : SimulationState
    //  data class ProcessingEdge(val id: String) : SimulationState
    data class NeighborSelection(
        override val code: String,
        val unvisitedNeighbors: List<String>,
        val callback: (List<String>) -> Unit
    ) : SimulationState

    data class Finished ( override val code: String): SimulationState
    // data class ExecutionAt(val nodeId: String) : SimulationState

    data class ColorChanged( override val code: String,val nodeColors: Set<Pair<NodeModel, ColorModel>>) : SimulationState
    data class ProcessingEdge( override val code: String,val edgeId: String) : SimulationState
    data class ExecutionAt( override val code: String,val nodeId: String) : SimulationState
    data class NodeProcessed( override val code: String,val nodeId: String) : SimulationState
    data class StartingNode( override val code: String,val nodeId: String) : SimulationState
    data class TraversingEdge( override val code: String,val id:String) : SimulationState
    data class TopologicalOrder (override val code: String,val order: List<String>) : SimulationState

}