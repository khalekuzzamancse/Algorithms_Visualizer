package graph.topological_sort.domain

import graph._core.domain.ColorModel
import graph._core.domain.NodeModel

sealed interface TopologicalSortState {
    val code:String
    //    data class ColorChanged(val nodes: Set<Pair<NodeModel, ColorModel>>) : SimulationState
    //  data class ProcessingEdge(val id: String) : SimulationState
    data class NeighborSelection(
        override val code: String,
        val unvisitedNeighbors: List<String>,
        val callback: (List<String>) -> Unit
    ) : TopologicalSortState

    data class Finished ( override val code: String): TopologicalSortState
    // data class ExecutionAt(val nodeId: String) : SimulationState

    data class ColorChanged( override val code: String,val nodeColors: Set<Pair<NodeModel, ColorModel>>) : TopologicalSortState
    data class ProcessingEdge( override val code: String,val edgeId: String) : TopologicalSortState
    data class ExecutionAt( override val code: String,val nodeId: String) : TopologicalSortState
    data class NodeProcessed( override val code: String,val nodeId: String) : TopologicalSortState
    data class StartingNode( override val code: String,val nodeId: String) : TopologicalSortState
    data class TraversingEdge( override val code: String,val id:String) : TopologicalSortState
    data class TopologicalOrder (override val code: String,val order: List<String>) : TopologicalSortState

}