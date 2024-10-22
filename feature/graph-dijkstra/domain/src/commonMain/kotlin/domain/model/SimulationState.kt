package domain.model


sealed interface SimulationState {
    val code:String
    data class Misc(override val code:String):SimulationState
    data class DistanceUpdated(val nodes: Set<NodeModel>,override val code:String):SimulationState
    data class ProcessingNode(val node: NodeModel,override val code:String):SimulationState
    data class ProcessingEdge(val edge: EdgeModel,override val code:String):SimulationState
    data class Finished(override val code:String) : SimulationState
}