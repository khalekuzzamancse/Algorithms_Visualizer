package database.dto

data class GraphEntity(
    val id:String,
    val nodes:List<NodeEntity>,
    val edges:List<EdgeEntity>
)
