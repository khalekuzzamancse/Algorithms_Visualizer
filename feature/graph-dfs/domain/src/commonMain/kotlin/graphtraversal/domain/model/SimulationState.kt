package graphtraversal.domain.model

sealed interface SimulationState {
    data class ColorChanged(val nodes: Set<Pair<NodeModel, ColorModel>>) : SimulationState
    data class ProcessingEdge(val id:String) : SimulationState
    data class BackEdgeDetected(val edge: EdgeModel) : SimulationState
    data object Finished : SimulationState
}