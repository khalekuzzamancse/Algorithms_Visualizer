@file:Suppress("unused")

package infrastructure.factory

import domain.model.EdgeModel
import domain.model.NeighborInfo
import domain.model.NodeModel
import domain.service.Graph

class GraphImpl(
    private val nodes: Set<NodeModel>,
    private val edges: Set<EdgeModel>
) : Graph {
    override fun getNeighborsWithWeights(node: NodeModel): List<NeighborInfo> {
        return edges.filter { it.u == node }
            .map { edge -> NeighborInfo(edgeId =edge.id , to=edge.v, weight =  edge.cost) }
    }

    override fun getEdgeCost(u: NodeModel, v: NodeModel): Int? {
        return edges.firstOrNull { it.u == u && it.v == v }?.cost
    }


    override fun getNodes() = nodes
}

