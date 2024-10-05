package graphtraversal.infrastructure.services

import graphtraversal.domain.model.ColorModel
import graphtraversal.domain.model.EdgeModel
import graphtraversal.domain.model.NodeModel


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
interface Graph {
    val sourceNodeId: String
    fun getAllNodeIds(): Set<String>
    fun updateParentOf(nodeId: String, parentId: String)
    fun getNode(nodeId: String): NodeModel?
    fun getNeighborsOf(nodeId: String): List<String>
    fun getParent(nodeId: String): NodeModel?
    fun findEdge(uId: String, vId: String): EdgeModel?
    fun updateColor(nodeId: String,color: ColorModel)
    fun getOneUnvisitedNeighbourOf(nodeId: String): String?
    fun getUnvisitedNeighbourOf(nodeId: String):List<String>

}
