package graphbfs.domain.model

sealed interface SimulationState {

    val code: String
    data class Start(override val code: String):SimulationState

    /**This color is not UI color, it the color(white, black,gray) for Algorithms traversal*/
    data class ColorChanged(
        val nodes: Set<Pair<NodeModel, ColorModel>>,
        override val code: String
    ) : SimulationState

    data class ProcessingEdge(val id: String, override val code: String) : SimulationState
    data class NeighborSelection(
        val unvisitedNeighbors: List<String>,
        val callback: (List<String>) -> Unit,
        override val code: String
    ) : SimulationState

    data class Finished(override val code: String) : SimulationState
    data class ExecutionAt(val nodeId: String, override val code: String) : SimulationState

}