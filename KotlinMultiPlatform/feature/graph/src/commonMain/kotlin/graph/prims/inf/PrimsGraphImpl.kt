package graph.prims.inf

import graph._core.domain.DomainNodeModel
import graph._core.domain.EdgeModel


class PrimsGraphImpl(
    private val nodes: Set<DomainNodeModel>,
    private val edges: Set<EdgeModel>,
    startNode: DomainNodeModel
) : Graph {

    private val adjacency: Map<String, DomainNodeModel> = nodes.associateBy { it.id }


    override fun getAllNodeIds(): Set<String> {
        return adjacency.keys
    }

    private val distanceFromParent = mutableMapOf<String, Int>()
    private val parent = mutableMapOf<String, String?>()

    private val adjacencyList: MutableMap<String, MutableList<Neighbor>> = mutableMapOf()

    init {
        nodes.forEach { node -> adjacencyList[node.id] = mutableListOf() }

        edges.forEach { edge ->
            //TODO:Throw Exception if cost is null
            adjacencyList[edge.u.id]?.add(Neighbor(edge.v.id, edge.cost!!))
            adjacencyList[edge.v.id]?.add(Neighbor(edge.u.id, edge.cost!!))
        }

        nodes.forEach { node ->
            distanceFromParent[node.id] = Int.MAX_VALUE
            parent[node.id] = null
        }
    }

    val startNodeId: String = startNode.id

    override fun getNeighborsOf(nodeId: String): List<Neighbor> {
        return adjacencyList[nodeId] ?: emptyList()
    }

    override fun updateParentOf(nodeId: String, parentId: String) {
        parent[nodeId] = parentId
    }

    override fun updateDistanceOf(nodeId: String, distance: Int) {
        distanceFromParent[nodeId] = distance
    }

    override fun getDistanceOf(nodeId: String): Int? {
        return distanceFromParent[nodeId]
    }

    override fun getNode(nodeId: String): DomainNodeModel? {
        return adjacency[nodeId]
    }

    override fun getParent(nodeId: String): DomainNodeModel? {
        val parentId = parent[nodeId]
        return if (parentId != null) adjacency[parentId] else null
    }

    override fun findEdge(uId: String, vId: String): EdgeModel? {
        return edges.find { (it.u.id == uId && it.v.id == vId) || (it.u.id == vId && it.v.id == uId) }
    }
}
