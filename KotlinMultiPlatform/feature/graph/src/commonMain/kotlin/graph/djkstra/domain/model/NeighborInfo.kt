package graph.djkstra.domain.model

data class NeighborInfo(
    val edgeId:String,
    val to: NodeModel,
    val weight: Int
)