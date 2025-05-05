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
    val u: DomainNodeModel,
    val v: DomainNodeModel,
    val cost: Int?=null //in case of Prims and Dijkstra cost is required
)

data class GraphModel(
    val isDirected: Boolean,
    val nodes: Set<DomainNodeModel>,
    val edges:Set<EdgeModel>,
    val source: DomainNodeModel
)
/**
 * - Do not need to unnecessary information such label
 * - In case of MST need to keep any distance for node
 */
data class DomainNodeModel(
    val id: String,
  //  val label:String=id ///need in case of MST
)


