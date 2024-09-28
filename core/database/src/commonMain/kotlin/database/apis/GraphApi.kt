package database.apis

import database.dto.GraphEntity


/**
 * API for managing graphs.
 * Exposes methods to retrieve and add graph data, with proper error handling.
 */
interface GraphApi {

    /**
     * Retrieves all graphs.
     * @return Result containing either a list of GraphEntity or an exception in case of failure.
     */
    suspend fun getAllGraphs(): Result<List<GraphEntity>>

    /**
     * Retrieves a graph by its primary key (ID).
     * @param graphId The ID of the graph to retrieve.
     * @return Result containing either the GraphEntity or an exception in case of failure.
     */
    suspend fun getGraphById(graphId: String): Result<GraphEntity?>

    /**
     * Adds a graph to the system.
     * Throws an exception if the operation fails.
     * @param graph The graph to be added.
     */
    suspend fun addGraph(graph: GraphEntity)

    /**
     * Adds a list of graphs to the system.
     * Throws an exception if the operation fails.
     * @param graphs List of graphs to be added.
     */
    suspend fun addGraphs(graphs: List<GraphEntity>)
}
