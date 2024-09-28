@file:Suppress("unused")
package database.dao

import androidx.room.*
import database.schema.GraphSchema

@Dao
internal interface GraphDao {

    @Upsert
    suspend fun upsertFaculty(graph: GraphSchema)

    @Upsert
    suspend fun upsertFaculties(graphs: List<GraphSchema>)

    @Query("SELECT * FROM graph_table")
    suspend fun getAllFaculties(): List<GraphSchema>

    @Query("SELECT * FROM graph_table WHERE primaryKey = :primaryKey")
    suspend fun getFacultyById(primaryKey: String): GraphSchema?

    @Delete
    suspend fun deleteFaculty(graph: GraphSchema)

    @Query("DELETE FROM graph_table")
    suspend fun clearAllFaculties()
}
