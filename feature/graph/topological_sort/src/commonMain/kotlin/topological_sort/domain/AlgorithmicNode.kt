package topological_sort.domain
/**
 * -  This class is specifically designed to be used apply the algorithms to the graph,do not use it UI or other purpose
 * - This class used to provide loose coupling to stat we need to use other module class to apply algorithm
 */
data class AlgorithmicNode(
    val id: String,
    val label: String,
    val  color: GraphNodeColor = GraphNodeColor.White
) {
    override fun toString() = label

    /**
     * - Important to override the equal otherwise can cause unexpected result
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AlgorithmicNode) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
