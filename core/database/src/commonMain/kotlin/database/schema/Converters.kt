package database.schema

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class Converters {
    @TypeConverter
    fun fromCoordinateSchema(coordinate: CoordinateSchema): String {
        return Json.encodeToString(coordinate)
    }

    @TypeConverter
    fun toCoordinateSchema(data: String): CoordinateSchema {
        return Json.decodeFromString(data)
    }

    @TypeConverter
    fun fromNodeSchema(nodes: List<NodeSchema>): String {
        return Json.encodeToString(nodes)
    }

    @TypeConverter
    fun toNodeSchema(data: String): List<NodeSchema> {
        return Json.decodeFromString(data)
    }

    @TypeConverter
    fun fromEdgeSchema(edges: List<EdgeSchema>): String {
        return Json.encodeToString(edges)
    }

    @TypeConverter
    fun toEdgeSchema(data: String): List<EdgeSchema> {
        return Json.decodeFromString(data)
    }
}
