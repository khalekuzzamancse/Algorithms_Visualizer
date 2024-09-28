package database.factory

import database.apis.GraphApi
import database.dao.GraphDao
import database.dto.GraphEntity
import database.dto.GraphMapper.toEntity
import database.dto.GraphMapper.toSchema
import database.schema.GraphSchema

class GraphApiImpl internal constructor(
    private val graphDao: GraphDao // DAO for interacting with the database
) : GraphApi {

    override suspend fun getAllGraphs(): Result<List<GraphEntity>> {
        return try {
            val graphSchemas = graphDao.getAllFaculties() // Retrieve all GraphSchema from the DB
            val graphEntities = graphSchemas.map { it.toEntity() } // Convert schemas to entities
            Result.success(graphEntities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGraphById(graphId: String): Result<GraphEntity?> {
        return try {
            val graphSchema = graphDao.getFacultyById(graphId) // Retrieve GraphSchema by ID
            val graphEntity = graphSchema?.toEntity() // Convert schema to entity
            Result.success(graphEntity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addGraph(graph: GraphEntity) {
        try {
            val graphSchema = graph.toSchema() // Convert entity to schema
            graphDao.upsertFaculty(graphSchema) // Insert the GraphSchema into the DB
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun addGraphs(graphs: List<GraphEntity>) {
        try {
            val graphSchemas = graphs.map { it.toSchema() } // Convert entities to schemas
            graphDao.upsertFaculties(graphSchemas) // Insert the GraphSchemas into the DB
        } catch (e: Exception) {
            throw e
        }
    }
}


// Convert GraphEntity to GraphSchema
internal fun GraphEntity.toSchema(): GraphSchema {
    return GraphSchema(
        primaryKey = this.id, // Generate or assign a primary key
        nodes = this.nodes.map { it.toSchema() },
        edges = this.edges.map { it.toSchema() }
    )
}

// Convert GraphSchema to GraphEntity
internal fun GraphSchema.toEntity(): GraphEntity {
    return GraphEntity(
        nodes = this.nodes.map { it.toEntity() },
        edges = this.edges.map { it.toEntity() },
        id = this.primaryKey
    )
}

