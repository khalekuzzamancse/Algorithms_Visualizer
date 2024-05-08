package graph_editor.domain

/**
 * - Edge for making the adjacent list and apply algorithm for traversal
 * - Since this related to business logic,that is why do not keep UI related info such as position,color,..etc
 * - Instead of keeping the whole node reference,it also okay to keep only the node id,but for debugging purpose we need
 * the label of the node,that is why keep the [Node],since [Node] is small class so it is okay
 */
data class Edge(
    val startNode: Node,
    val endNode: Node,
    val cost:String?=null
){
    override fun toString(): String {
        return "(${startNode.label},${endNode.label})" //for debugging purpose overriding
    }
}

