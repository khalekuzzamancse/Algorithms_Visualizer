package infrastructure

import mst.domain.model.EdgeModel
import mst.domain.model.NodeModel
import tree.infrastructure.factory.GraphImpl
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
            EdgeModel(id = "1", u = nodeA, v = nodeB, cost = 10),
            EdgeModel(id = "2", nodeA, nodeE, 5),
            EdgeModel(id = "3", nodeB, nodeE, 2),
            EdgeModel(id = "4", nodeE, nodeB, 3),
            EdgeModel(id = "5", nodeB, nodeC, 1),
            EdgeModel(id = "6", nodeE, nodeC, 9),
            EdgeModel(id = "7", nodeE, nodeD, 2),
            EdgeModel(id = "8", nodeD, nodeA, 7),
            EdgeModel(id = "9", nodeC, nodeD, 4),
            EdgeModel(id = "10", nodeD, nodeC, 6)
        )


// Create the graph
        val graph = GraphImpl(
            nodes = setOf(nodeA, nodeB, nodeC, nodeD, nodeE),
            edges = edges
        )
//        DijkstraAlgorithm().runDijkstra(graph, startNode = nodeA)
//        graph.getNodes().forEach {
//            println(it)
//        }

    }
}