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
    val u: graph._core.domain.NodeModel,
    val v: graph._core.domain.NodeModel,
)
data class GraphModel(
    val isDirected: Boolean,
    val nodes: Set<graph._core.domain.NodeModel>,
    val edges:Set<EdgeModel>,
    val source: graph._core.domain.NodeModel
)
/**
 * - Do not need to unnecessary information such label
 * - In case of MST need to keep any distance for node
 */
data class NodeModel(
    val id: String
)


