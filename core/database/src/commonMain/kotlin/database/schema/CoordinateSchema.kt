package database.schema

import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
data class CoordinateSchema (
    val x:Float,
    val y:Float
)
internal class CoordinateConverter {

    @TypeConverter
    fun fromCoordinate(coordinate: CoordinateSchema): String {
        return Json.encodeToString(coordinate)
    }

    @TypeConverter
    fun toCoordinate(coordinate: String): CoordinateSchema{
        return  Json.decodeFromString(coordinate)
    }
}