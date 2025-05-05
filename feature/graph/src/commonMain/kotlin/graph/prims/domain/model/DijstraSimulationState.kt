package graph.prims.domain.model

import graph._core.domain.DomainNodeModel
import graph._core.domain.EdgeModel


sealed interface DijstraSimulationState {
    val code:String
    data class Misc(override val code:String): DijstraSimulationState
    data class ProcessingNode(val node: DomainNodeModel, override val code:String): DijstraSimulationState
    data class ProcessingEdge(val edge: EdgeModel, override val code:String):
        DijstraSimulationState
    data class Finished(override val code:String) : DijstraSimulationState
}