package domain.model


sealed interface SimulationState {
    data class DistanceUpdated(val nodes: Set<NodeModel>):SimulationState
    data class ProcessingNode(val node: NodeModel):SimulationState
    data class ProcessingEdge(val edge: EdgeModel):SimulationState
    data object Finished : SimulationState
}