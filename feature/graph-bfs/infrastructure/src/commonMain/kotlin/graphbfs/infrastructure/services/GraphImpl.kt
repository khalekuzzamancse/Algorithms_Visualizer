package graphbfs.infrastructure.services

import graphbfs.domain.model.ColorModel
import graphbfs.domain.model.EdgeModel
import graphbfs.domain.model.GraphModel
import graphbfs.domain.model.NodeModel

class GraphImpl(
    private val graph: GraphModel
) : Graph {

    private val adjacency: Map<String, NodeModel> = graph.nodes.associateBy { it.id }
    private val colors: MutableMap<String, ColorModel> = mutableMapOf()

    override fun getAllNodeIds(): Set<String> {
        return adjacency.keys
    }


    private val parent = mutableMapOf<String, String?>()

    private val adjacencyList: MutableMap<String, MutableList<String>> = mutableMapOf()

    init {
        graph.nodes.forEach { node -> adjacencyList[node.id] = mutableListOf() }

        graph.edges.forEach { edge ->
            adjacencyList[edge.u.id]?.add(edge.v.id)
            if (!graph.isDirected)
            adjacencyList[edge.v.id]?.add(edge.u.id)
        }

        graph.nodes.forEach { node ->
            parent[node.id] = null
        }

    }

    override val sourceNodeId: String = graph.source.id

    override fun getNeighborsOf(nodeId: String): List<String> {
        return adjacencyList[nodeId] ?: emptyList()
    }

    override fun updateParentOf(nodeId: String, parentId: String) {
        parent[nodeId] = parentId
    }

    override fun getNode(nodeId: String): NodeModel? {
        return adjacency[nodeId]
    }

    override fun getParent(nodeId: String): NodeModel? {
        val parentId = parent[nodeId]
        return if (parentId != null) adjacency[parentId] else null
    }

    override fun findEdge(uId: String, vId: String): EdgeModel? {
        return if (graph.isDirected)
            graph.edges.find { (it.u.id == uId && it.v.id == vId) }
        else
            graph.edges.find { (it.u.id == uId && it.v.id == vId) || (it.u.id == vId && it.v.id == uId) }
    }

    override fun updateColor(nodeId: String, color: ColorModel) {
        colors[nodeId] = color
    }

    override fun getOneUnvisitedNeighbourOf(nodeId: String): String? {
        return getNeighborsOf(nodeId)
            .firstOrNull { neighborId ->
                colors[neighborId] == ColorModel.White
            }
    }

    override fun getUnvisitedNeighbourOf(nodeId: String): List<String> {
        return getNeighborsOf(nodeId)
            .filter { neighborId ->
                val nodeColor=colors[neighborId]
                nodeColor == ColorModel.White
            }
    }
    // toString method to print the adjacency list nicely
    override fun toString(): String {
       // println("isDirected:${graph.isDirected}")
        val builder = StringBuilder()
        builder.append("Graph Adjacency List:\n")
        adjacencyList.forEach { (nodeId, neighbors) ->
            builder.append("$nodeId -> ${neighbors.joinToString(", ")}\n")
        }
        return builder.toString()
    }

}