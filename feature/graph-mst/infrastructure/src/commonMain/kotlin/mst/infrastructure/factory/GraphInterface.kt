package mst.infrastructure.factory

import mst.domain.model.EdgeModel
import mst.domain.model.NodeModel
import mst.infrastructure.Neighbor

/**
 * The GraphInterface provides essential methods for interacting with a graph
 * where nodes, edges, distances, and parent relationships are stored. 
 *
 * This design emphasizes maintaining a single source of truth for the nodes,
 * exposing only their IDs instead of the `NodeModel` objects directly.
 * 
 * ### Key Concept:
 * - **Single Source of Truth**: If multiple references or copies of node objects
 *   are maintained, it can lead to inconsistencies that are difficult to debug.
 *   By exposing node IDs and managing the internal state through these IDs, 
 *   this interface ensures that the graph remains consistent, and algorithms can 
 *   work reliably without the risk of referencing outdated or incorrect data.
 */
interface GraphInterface {

    /**
     * Returns all the node IDs present in the graph.
     *
     * @return Set of node IDs.
     */
    fun getAllNodeIds(): Set<String>

    /**
     * Returns a list of neighbors for the specified node by its ID.
     *
     * @param nodeId The ID of the node whose neighbors are requested.
     * @return List of `Neighbor` objects representing the neighboring nodes.
     */
    fun getNeighborsOf(nodeId: String): List<Neighbor>

    /**
     * Updates the parent of a node by its ID.
     *
     * @param nodeId The ID of the node whose parent is being updated.
     * @param parentId The ID of the parent node.
     */
    fun updateParentOf(nodeId: String, parentId: String)

    /**
     * Updates the distance for a node by its ID.
     *
     * @param nodeId The ID of the node whose distance is being updated.
     * @param distance The new distance value to set for the node.
     */
    fun updateDistanceOf(nodeId: String, distance: Int)

    /**
     * Returns the distance for a node by its ID.
     *
     * @param nodeId The ID of the node whose distance is requested.
     * @return The current distance of the node or null if not set.
     */
    fun getDistanceOf(nodeId: String): Int?

    /**
     * Returns the `NodeModel` object for a node by its ID.
     *
     * @param nodeId The ID of the node.
     * @return The `NodeModel` object representing the node or null if the node doesn't exist.
     */
    fun getNode(nodeId: String): NodeModel?

    /**
     * Returns the parent `NodeModel` object for a node by its ID.
     *
     * @param nodeId The ID of the node whose parent is requested.
     * @return The `NodeModel` object representing the parent node or null if no parent is set.
     */
    fun getParent(nodeId: String): NodeModel?

    /**
     * Finds and returns the `EdgeModel` object representing the edge between two nodes by their IDs.
     *
     * @param uId The ID of the first node.
     * @param vId The ID of the second node.
     * @return The `EdgeModel` object representing the edge, or null if no edge exists between the nodes.
     */
    fun findEdge(uId: String, vId: String): EdgeModel?
}
