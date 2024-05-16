package topological_sort.domain
/**
 * -  This class is specifically designed to be used apply the algorithms to the graph,do not use it UI or other purpose
 * - This class used to provide loose coupling to stat we need to use other module class to apply algorithm
 */
data class AlgorithmicEdge(
    val from: AlgorithmicNode,
    val to: AlgorithmicNode,
    val cost:String?
)
