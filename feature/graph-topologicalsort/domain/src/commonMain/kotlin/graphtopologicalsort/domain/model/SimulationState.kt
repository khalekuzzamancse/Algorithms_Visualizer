package graphtopologicalsort.domain.model

sealed interface SimulationState {

    //    data class ColorChanged(val nodes: Set<Pair<NodeModel, ColorModel>>) : SimulationState
    //  data class ProcessingEdge(val id: String) : SimulationState
    data class NeighborSelection(
        val unvisitedNeighbors: List<String>,
        val callback: (List<String>) -> Unit
    ) : SimulationState

    data object Finished : SimulationState
    // data class ExecutionAt(val nodeId: String) : SimulationState

    data class ColorChanged(val nodeColors: Set<Pair<NodeModel, ColorModel>>) : SimulationState
    data class ProcessingEdge(val edgeId: String) : SimulationState
    data class ExecutionAt(val nodeId: String) : SimulationState
    data class NodeProcessed(val nodeId: String) : SimulationState
    data class StartingNode(val nodeId: String) : SimulationState
    data class TraversingEdge(val id:String) : SimulationState
    data class TopologicalOrder(val order: List<String>) : SimulationState

}