package database.dto


data class EdgeEntity(
    val id: String,
    val start: CoordinateEntity,
    val end: CoordinateEntity,
    val control: CoordinateEntity,
    val cost: String?,
    val directed: Boolean,
)

