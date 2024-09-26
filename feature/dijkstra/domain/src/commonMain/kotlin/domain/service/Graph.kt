package domain.service

import domain.model.NeighborInfo
import domain.model.NodeModel

/**
 * Interface representing a graph structure for use in Dijkstra's algorithm.
 *
 * Dijkstra only requires the ability to:
 * - Retrieve the list of neighboring nodes for a given node, along with the edge weights.
 * - Retrieve the cost of an edge between two nodes.
 * - Retrieve the set of all nodes in the graph.
 *
 * This interface abstracts away other unnecessary details to focus on what the Dijkstra algorithm needs.
 */
interface Graph {
    
    /**
     * Returns a list of neighboring nodes for a given [node], along with the weight of the edges connecting them.
     *
     * @param node The node for which the neighbors are to be fetched.
     * @return A list of [NeighborInfo], where each item represents a neighboring node and the weight of the edge connecting to it.
     */
    fun getNeighborsWithWeights(node: NodeModel): List<NeighborInfo>

    /**
     * Returns the cost of the edge between two nodes [u] and [v].
     *
     * @param u The starting node.
     * @param v The destination node.
     * @return The cost of the edge, or `null` if no edge exists between the two nodes.
     */
    fun getEdgeCost(u: NodeModel, v: NodeModel): Int?

    /**
     * Returns the set of all nodes in the graph.
     *
     * @return A set of [NodeModel] representing all the nodes in the graph.
     */
    fun getNodes(): Set<NodeModel>
}
