@file:Suppress("unused")

package mst.infrastructure.factory

import mst.domain.model.EdgeModel
import mst.domain.model.NeighborInfo
import mst.domain.model.NodeModel
import mst.domain.service.Graph

class GraphImpl(
    private val nodes: Set<NodeModel>,
    private val edges: Set<EdgeModel>
) : Graph {

    // Adjacency list directly references the NodeModel instances
    override val adjacencyList: MutableMap<NodeModel, MutableList<NeighborInfo>> = mutableMapOf()

    init {
        // Initialize adjacency list with empty lists
        nodes.forEach { node -> adjacencyList[node] = mutableListOf() }

        // Populate adjacency list based on edges (undirected graph)
        edges.forEach { edge ->
            adjacencyList[edge.u]?.add(
                NeighborInfo(
                    edgeId = edge.id,
                    neighbour = edge.v, // Direct reference to the actual NodeModel
                    weight = edge.cost
                )
            )
            adjacencyList[edge.v]?.add(
                NeighborInfo(
                    edgeId = edge.id,
                    neighbour = edge.u, // Direct reference to the actual NodeModel
                    weight = edge.cost
                )
            )
        }
    }

    // Find edge between two nodes (undirected)
    override fun findEdge(u: NodeModel, v: NodeModel): EdgeModel? {
        return edges.find { (it.u == u && it.v == v) || (it.u == v && it.v == u) }
    }

    // Get neighbors with weights for a node using direct reference from the adjacency list
    override fun getNeighborsWithWeights(node: NodeModel): List<NeighborInfo> {
        // Access the adjacency list directly using the node
        return adjacencyList[node] ?: emptyList()
    }

    // Get the cost of an edge between two nodes
    override fun getEdgeCost(u: NodeModel, v: NodeModel): Int? {
        return edges.firstOrNull { (it.u == u && it.v == v) || (it.u == v && it.v == u) }?.cost
    }

    // Return the set of nodes in the graph
    override fun getNodes(): Set<NodeModel> = nodes

    // Return a string representation of the adjacency list
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Graph Adjacency List:\n")
        adjacencyList.forEach { (node, neighbors) ->
            sb.append("${node.label}: ")
            neighbors.forEach { neighbor ->
                sb.append("(${neighbor.neighbour.label}, cost: ${neighbor.weight}) ")
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}
