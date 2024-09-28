package database.schema

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity("graph_table")
internal data class GraphSchema(
   @PrimaryKey val primaryKey: String,
    @TypeConverters(NodeSchema::class) val nodes: List<NodeSchema>,
    @TypeConverters(EdgeSchema::class) val edges: List<EdgeSchema>
)
