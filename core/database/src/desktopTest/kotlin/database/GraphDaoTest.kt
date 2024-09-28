@file:Suppress("SpellCheckingInspection")

package database

import database.dao.GraphDao
import database.schema.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GraphDaoTest {

    private lateinit var db: Database
    private lateinit var graphDao: GraphDao

    @Before
    fun createDb() {
        db = getDatabase() // Assuming this function sets up the in-memory database
        graphDao = db.graphDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testUpsertAndRetrieveGraph() = runBlocking {
        val node = NodeSchema(
            id = "node1",
            distance = "5",
            label = "Node 1",
            topLeft = CoordinateSchema(0.0f, 0.0f),
            exactSizePx = 100.0f
        )

        val edge = EdgeSchema(
            id = "edge1",
            start = CoordinateSchema(0.0f, 0.0f),
            end = CoordinateSchema(1.0f, 1.0f),
            control = CoordinateSchema(0.5f, 0.5f),
            cost = "10",
            directed = true
        )

        val graph = GraphSchema(
            primaryKey = "graph1",
            nodes = listOf(node),
            edges = listOf(edge)
        )

        graphDao.upsertFaculty(graph)

        val retrievedGraph = graphDao.getFacultyById(graph.primaryKey)
        println("Inserted graph: ${graph.primaryKey}")
        println("Retrieved graph: ${retrievedGraph?.primaryKey}")

        assertEquals(graph.primaryKey, retrievedGraph?.primaryKey)
    }

    @Test
    fun testClearGraphs() = runBlocking {
        val node = NodeSchema(
            id = "node1",
            distance = "5",
            label = "Node 1",
            topLeft = CoordinateSchema(0.0f, 0.0f),
            exactSizePx = 100.0f
        )

        val edge = EdgeSchema(
            id = "edge1",
            start = CoordinateSchema(0.0f, 0.0f),
            end = CoordinateSchema(1.0f, 1.0f),
            control = CoordinateSchema(0.5f, 0.5f),
            cost = "10",
            directed = true
        )

        val graph = GraphSchema(
            primaryKey = "graph1",
            nodes = listOf(node),
            edges = listOf(edge)
        )

        graphDao.upsertFaculty(graph)
        println("Inserted graph: ${graph.primaryKey}")

        graphDao.clearAllFaculties()

        val retrievedGraph = graphDao.getFacultyById(graph.primaryKey)
        println("Graph after clearing: ${retrievedGraph?.primaryKey}")

        assertNull(retrievedGraph)
    }

    @Test
    fun testUpdateGraph() = runBlocking {
        val node = NodeSchema(
            id = "node1",
            distance = "5",
            label = "Node 1",
            topLeft = CoordinateSchema(0.0f, 0.0f),
            exactSizePx = 100.0f
        )

        val edge = EdgeSchema(
            id = "edge1",
            start = CoordinateSchema(0.0f, 0.0f),
            end = CoordinateSchema(1.0f, 1.0f),
            control = CoordinateSchema(0.5f, 0.5f),
            cost = "10",
            directed = true
        )

        val graph = GraphSchema(
            primaryKey = "graph1",
            nodes = listOf(node),
            edges = listOf(edge)
        )

        graphDao.upsertFaculty(graph)
        println("Inserted graph: ${graph.primaryKey}")

        val updatedNode = node.copy(label = "Updated Node 1")
        val updatedGraph = graph.copy(nodes = listOf(updatedNode))

        graphDao.upsertFaculty(updatedGraph)
        println("Updated graph with node label: ${updatedNode.label}")

        val retrievedGraph = graphDao.getFacultyById(graph.primaryKey)
        println("Retrieved updated graph: ${retrievedGraph?.nodes?.first()?.label}")

        assertEquals("Updated Node 1", retrievedGraph?.nodes?.first()?.label)
    }

    @Test
    fun testRetrieveEmptyDatabase() = runBlocking {
        val retrievedGraphs = graphDao.getAllFaculties()
        println("Graphs from empty database: $retrievedGraphs")

        assertEquals(0, retrievedGraphs.size)
    }
}
