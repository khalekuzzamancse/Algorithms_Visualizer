package database

import database.apis.GraphApi
import database.dto.CoordinateEntity
import database.dto.EdgeEntity
import database.dto.GraphEntity
import database.dto.NodeEntity
import database.factory.DatabaseFactory
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class GraphApiTest {

    private lateinit var graphApi: GraphApi

    @Before
    fun setup() {
        // Create the GraphApi using the factory
        graphApi = DatabaseFactory().createGraphApi()
    }

    @After
    fun tearDown() {
        // Assuming there's a method to clear the database or reset the state
        runBlocking { graphApi.addGraphs(emptyList()) }
    }

    @Test
    fun testAddAndRetrieveGraph(): Unit = runBlocking {
        // Create sample NodeEntity and EdgeEntity
        val node = NodeEntity(
            id = "node1",
            distance = "10",
            label = "Node 1",
            topLeft = CoordinateEntity(0.0f, 0.0f),
            exactSizePx = 50.0f
        )

        val edge = EdgeEntity(
            id = "edge1",
            start = CoordinateEntity(0.0f, 0.0f),
            end = CoordinateEntity(1.0f, 1.0f),
            control = CoordinateEntity(0.5f, 0.5f),
            cost = "5",
            directed = true
        )

        // Create a GraphEntity
        val graphEntity = GraphEntity(
            id = "graph1",
            nodes = listOf(node),
            edges = listOf(edge)
        )

        // Add the graph to the system
        graphApi.addGraph(graphEntity)

        // Retrieve the graph by ID (assuming primaryKey is generated within the system)
        val result = graphApi.getGraphById("graph1") // Assuming you know the primary key
        result.onSuccess { retrievedGraph ->
            assertNotNull(retrievedGraph)
            assertEquals(1, retrievedGraph?.nodes?.size)
            assertEquals(1, retrievedGraph?.edges?.size)
            assertEquals("Node 1", retrievedGraph?.nodes?.first()?.label)
        }.onFailure { error ->
            assertNull(error, "Error occurred while retrieving graph")
        }
    }

    @Test
    fun testRetrieveAllGraphs(): Unit = runBlocking {
        // Create sample nodes and edges for two graphs
        val node1 = NodeEntity(
            id = "node1",
            distance = "10",
            label = "Node 1",
            topLeft = CoordinateEntity(0.0f, 0.0f),
            exactSizePx = 50.0f
        )
        val node2 = NodeEntity(
            id = "node2",
            distance = "20",
            label = "Node 2",
            topLeft = CoordinateEntity(1.0f, 1.0f),
            exactSizePx = 50.0f
        )

        val edge1 = EdgeEntity(
            id = "edge1",
            start = CoordinateEntity(0.0f, 0.0f),
            end = CoordinateEntity(1.0f, 1.0f),
            control = CoordinateEntity(0.5f, 0.5f),
            cost = "5",
            directed = true
        )
        val edge2 = EdgeEntity(
            id = "edge2",
            start = CoordinateEntity(1.0f, 1.0f),
            end = CoordinateEntity(2.0f, 2.0f),
            control = CoordinateEntity(1.5f, 1.5f),
            cost = "7",
            directed = true
        )

        val graph1 = GraphEntity(id = "1", nodes = listOf(node1), edges = listOf(edge1))
        val graph2 = GraphEntity(id = "2", nodes = listOf(node2), edges = listOf(edge2))

        // Add both graphs
        graphApi.addGraphs(listOf(graph1, graph2))

        // Retrieve all graphs
        val result = graphApi.getAllGraphs()
        result.onSuccess { graphs ->
            println("Results:$graphs")
            assertEquals(2, graphs.size)
        }.onFailure { error ->
            assertNull(error, "Error occurred while retrieving all graphs")
        }
    }

    @Test
    fun testAddGraphAndRetrieveEmpty(): Unit = runBlocking {
        // Attempt to retrieve a graph that does not exist
        val result = graphApi.getGraphById("non_existing_id")
        result.onSuccess { graph ->
            assertNull(graph)
        }.onFailure { error ->
            assertNull(error, "Error occurred while retrieving non-existing graph")
        }
    }
}
