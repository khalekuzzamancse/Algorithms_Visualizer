package database.schema

import androidx.room.TypeConverters
import kotlinx.serialization.Serializable

@Serializable
data class NodeSchema(
    val id: String,
    val distance:String?,
    val label: String,
    @TypeConverters(CoordinateSchema::class) val topLeft: CoordinateSchema,
    val exactSizePx: Float,
)

//@Entity(tableName = "node_table")
//data class NodeSchema(
//    val id: String,
//    val graphName:String,//use to filter the graph for a particular node
//    val distance:String?,
//    val label: String,
//    @TypeConverters(CoordinateSchema::class) val topLeft: CoordinateSchema,
//    val exactSizePx: Float,
//)