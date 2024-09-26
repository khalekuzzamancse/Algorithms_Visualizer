package infrastructure

import domain.model.EdgeModel
import domain.model.NodeModel
import infrastructure.factory.DijkstraAlgorithm
import infrastructure.factory.GraphImpl
import kotlin.test.Test

class DijkstraImplementationTest {
    @Test
    fun test(){
        // Define nodes
        val nodeA = NodeModel(id = "A", label = "A")
        val nodeB = NodeModel(id = "B", label = "B")
        val nodeC = NodeModel(id = "C", label = "C")
        val nodeD = NodeModel(id = "D", label = "D")
        val nodeE = NodeModel(id = "E", label = "E")

// Define edges with weights
        val edges = setOf(
            EdgeModel(nodeA, nodeB, 10),
            EdgeModel(nodeA, nodeE, 5),
            EdgeModel(nodeB, nodeE, 2),
            EdgeModel(nodeE, nodeB, 3),
            EdgeModel(nodeB, nodeC, 1),
            EdgeModel(nodeE, nodeC, 9),
            EdgeModel(nodeE, nodeD, 2),
            EdgeModel(nodeD, nodeA, 7),
            EdgeModel(nodeC, nodeD, 4),
            EdgeModel(nodeD, nodeC, 6)
        )

// Create the graph
        val graph = GraphImpl(
            nodes = setOf(nodeA, nodeB, nodeC, nodeD, nodeE),
            edges = edges
        )
        DijkstraAlgorithm().runDijkstra(graph, startNode = nodeA)
        graph.getNodes().forEach {
            println(it)
        }

    }
}