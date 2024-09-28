package database.dto

data class NodeEntity(
    val id: String,
    val distance: String?,
    val label: String,
    val topLeft: CoordinateEntity,
    val exactSizePx: Float,
)