//@file:Suppress("unused")
//
//package infrastructure.mis
//
//import domain.model.EdgeModel
//import domain.model.NeighborInfo
//import domain.model.NodeModel
//import domain.service.Graph
//class PrimsGraph(
//   nodes: Set<NodeModel>,
//    edges: Set<EdgeModel>
//) : Graph {
//
//
//    private val adjacencyList: MutableMap<NodeModel, MutableList<NeighborInfo>> = mutableMapOf()
//
//    init {
//
//        nodes.forEach { node -> adjacencyList[node] = mutableListOf() }
//        edges.forEach { edge ->
//            adjacencyList[edge.u]?.add(NeighborInfo(edgeId = edge.id, to = edge.v, weight = edge.cost))
//            //undirected in case of Prime's
//            adjacencyList[edge.v]?.add(NeighborInfo(edgeId = edge.id, to = edge.u, weight = edge.cost))
//        }
//        println("${this.javaClass.simpleName}\n ${toString()}}")
//
//    }
//
//
//    override fun getNeighborsWithWeights(node: NodeModel): List<NeighborInfo> {
//        return adjacencyList[node] ?: emptyList()
//    }
//
//
//    override fun getEdgeCost(u: NodeModel, v: NodeModel): Int? {
//        return edges.firstOrNull { it.u == u && it.v == v }?.cost
//    }
//
//
//    override fun getNodes() = nodes
//
//    override fun toString(): String {
//        val sb = StringBuilder()
//        sb.append("Graph Adjacency List:\n")
//        adjacencyList.forEach { (node, neighbors) ->
//            sb.append("${node.label}: ")
//            neighbors.forEach { neighbor ->
//                sb.append("(${neighbor.to.label}, cost: ${neighbor.weight}) ")
//            }
//            sb.append("\n")
//        }
//        return sb.toString()
//    }
//}
//
