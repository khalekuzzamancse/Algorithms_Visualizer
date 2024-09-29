package tree.domain.model

/**
 * - in case of MST there is edge cost
 */
data class EdgeModel(
    val id: String,
    val u: NodeModel,
    val v: NodeModel,
    val cost: Int,
)