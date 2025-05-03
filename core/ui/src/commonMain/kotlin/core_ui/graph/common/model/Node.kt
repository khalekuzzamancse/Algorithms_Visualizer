package core_ui.graph.common.model

/**
 * - Node for making the adjacent list and apply algorithm for traversal
 * - Since this related to business logic,that is why do not keep UI related info such as position,color,..etc
 */
data class Node(
    val id: String,
    val label: String
){

    override fun toString(): String {
        return label //for debugging purpose overriding
    }
}