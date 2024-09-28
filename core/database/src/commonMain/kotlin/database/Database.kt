package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import database.dao.GraphDao
import database.schema.Converters
import database.schema.CoordinateConverter
import database.schema.EdgeSchema
import database.schema.GraphSchema
import database.schema.NodeSchema

@Database(
    entities = [
        GraphSchema::class,
    ],
    version = 1
)
@TypeConverters(Converters::class)
internal abstract class Database : RoomDatabase() {
    abstract fun graphDao(): GraphDao

}