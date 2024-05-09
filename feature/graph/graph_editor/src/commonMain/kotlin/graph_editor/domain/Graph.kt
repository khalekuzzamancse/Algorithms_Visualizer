package graph_editor.domain

/**
 * - This is just for exacting the Graph such as Adjacency list list and other info for traversal
 * - Do not use this class for other purpose,instead of copy  the necessary data form here made your own classes according  to your
 * requirement
 */
data class Graph(
    val nodes: Set<Node>,
    val edges: Set<Edge>
)
//{
//
//    /**
//     * - Given that this application is designed for visualization, and considering that the maximum number of nodes a
//     * user will handle at any given time is 50, it is practical and simple to store all nodes directly.
//     * This approach is feasible due to the graph's relatively small size.
//     */
//
//    fun toAdjacencyList(): Map<Node, List<Node>> {
//        val adjacencyList = mutableMapOf<Node, MutableList<Node>>()
//
//        // Initialize the adjacency list with empty lists for each node
//        nodes.forEach { node ->
//            adjacencyList[node] = mutableListOf()
//        }
//
//        // Populate the adjacency list with edges
//        edges.forEach { edge ->
//            adjacencyList[edge.from]?.add(edge.to)
//            if (!isDirected) {
//                adjacencyList[edge.to]?.add(edge.from)
//            }
//        }
//
//        return adjacencyList
//    }
//}