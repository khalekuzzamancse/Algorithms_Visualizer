@file:Suppress("unused")

package graph.djkstra.inf

import graph.djkstra.domain.model.EdgeModel
import graph.djkstra.domain.model.NeighborInfo
import graph.djkstra.domain.model.NodeModel
import graph.djkstra.domain.service.Graph

class GraphImpl(
    private val nodes: Set<NodeModel>,
    private val edges: Set<EdgeModel>
) : Graph {


    private val adjacencyList: MutableMap<NodeModel, MutableList<NeighborInfo>> = mutableMapOf()

    init {

        nodes.forEach { node -> adjacencyList[node] = mutableListOf() }
        edges.forEach { edge ->
            adjacencyList[edge.u]?.add(NeighborInfo(edgeId = edge.id, to = edge.v, weight = edge.cost))
        }

    }


    override fun getNeighborsWithWeights(node: NodeModel): List<NeighborInfo> {
        return adjacencyList[node] ?: emptyList()
    }


    override fun getEdgeCost(u: NodeModel, v: NodeModel): Int? {
        return edges.firstOrNull { it.u == u && it.v == v }?.cost
    }


    override fun getNodes() = nodes

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Graph Adjacency List:\n")
        adjacencyList.forEach { (node, neighbors) ->
            sb.append("${node.label}: ")
            neighbors.forEach { neighbor ->
                sb.append("(${neighbor.to.label}, cost: ${neighbor.weight}) ")
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}

