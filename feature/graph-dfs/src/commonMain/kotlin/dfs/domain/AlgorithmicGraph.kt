package dfs.domain

/**
 * -  This class is specifically designed to be used apply the algorithms to the graph,do not use it UI or other purpose
 * - This class used to provide loose coupling to stat we need to use other module class to apply algorithm
 */
class AlgorithmicGraph(
    private val undirected: Boolean,
    val nodes: List<AlgorithmicNode>,
    private val edges: List<AlgorithmicEdge>,
) {

    override fun toString(): String {
        return "AdjacentList:${adjacencyList}"
    }

    /**
     * - Given that this application is designed for visualization, and considering that the maximum number of nodes a
     * user will handle at any given time is 50, it is practical and simple to store all nodes directly.
     * This approach is feasible due to the graph's relatively small size.
     */

    /*
    - Once the adjacency is created,We want to save that
     */
    val adjacencyList: Map<AlgorithmicNode, List<AlgorithmicNode>>
        get() {
            val adjacencyList = mutableMapOf<AlgorithmicNode, MutableList<AlgorithmicNode>>()
            // Initialize the adjacency list with empty lists for each node
            nodes.forEach { node ->
                adjacencyList[node] = mutableListOf()
            }

            // Populate the adjacency list with edges
            edges.forEach { edge ->
                adjacencyList[edge.from]?.add(edge.to)
                if (undirected) {
                    adjacencyList[edge.to]?.add(edge.from)
                }
            }

            return adjacencyList
        }
}
