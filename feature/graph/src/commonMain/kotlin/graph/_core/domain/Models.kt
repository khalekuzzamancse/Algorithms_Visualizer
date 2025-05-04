package graph._core.domain


enum class ColorModel {
    /**Not discovered yet*/
    White,
    /**Discovered but not process is not finished*/
    Gray,
    /**Processed means Discovered and  process is  finished*/
    Black,
}
data class EdgeModel(
    val id: String,
    val u: NodeModel,
    val v: NodeModel,
)
data class GraphModel(
    val isDirected: Boolean,
    val nodes: Set<NodeModel>,
    val edges:Set<EdgeModel>,
    val source: NodeModel
)
/**
 * - Do not need to unnecessary information such label
 * - In case of MST need to keep any distance for node
 */
data class NodeModel(
    val id: String
)


