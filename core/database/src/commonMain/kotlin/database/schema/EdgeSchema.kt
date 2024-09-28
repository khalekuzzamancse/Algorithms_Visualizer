package database.schema

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.serialization.Serializable


@Serializable
internal data class EdgeSchema(
    val id: String,
    @TypeConverters(CoordinateSchema::class) val start: CoordinateSchema,
    @TypeConverters(CoordinateSchema::class) val end: CoordinateSchema,
    @TypeConverters(CoordinateSchema::class) val control: CoordinateSchema,
    val cost: String?,
    val directed: Boolean,
)






//@Entity(tableName = "edge_table")
//@Serializable
//internal data class EdgeSchema(
//    @PrimaryKey val primaryKey: String,
//    val id: String,
//    @TypeConverters(CoordinateSchema::class) val start: CoordinateSchema,
//    @TypeConverters(CoordinateSchema::class) val end: CoordinateSchema,
//    @TypeConverters(CoordinateSchema::class) val control: CoordinateSchema,
//    val cost: String?,
//    val directed: Boolean,
//)
//
