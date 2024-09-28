package database
import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

internal fun getDatabase(context: Context): Database {
    val dbFile = context.getDatabasePath("database.db")
    return Room.databaseBuilder<Database>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()
}