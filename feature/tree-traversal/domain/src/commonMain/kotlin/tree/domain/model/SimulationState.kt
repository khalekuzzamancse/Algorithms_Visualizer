package tree.domain.model


sealed interface SimulationState {
    data class ProcessingNode(val node: NodeModel): SimulationState
    data class ProcessingEdge(val edge: EdgeModel): SimulationState
    data object Finished : SimulationState
}