package mst.domain.model

data class NeighborInfo(
    val edgeId:String,
    val neighbour: NodeModel,
    val weight: Int
)