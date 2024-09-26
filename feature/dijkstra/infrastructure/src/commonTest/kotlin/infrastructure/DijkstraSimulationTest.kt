package infrastructure

import domain.model.EdgeModel
import domain.model.NodeModel
import domain.model.SimulationState
import infrastructure.factory.DijkstraSimulation
import infrastructure.factory.GraphImpl
import kotlin.test.Test
import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds

class DijkstraSimulationTest {

    @Test
    fun test() = runBlocking {
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

        // Create an instance of the Dijkstra simulation
        val dijkstraSimulation = DijkstraSimulation().runDijkstra(graph, startNode = nodeA)

        // Iterate through the sequence lazily with a delay of 8 seconds
        dijkstraSimulation.forEach { state ->
            // Print each state
            when (state) {
                is SimulationState.ProcessingNode -> {
                    println("Processing Node: ${state.node.label}")
                }
                is SimulationState.ProcessingEdge -> {
                    println("Processing Edge: ${state.edge}")
                }
                is SimulationState.DistanceUpdated -> {
                    println("Distance Updated: ${state.nodes.joinToString { it.toString() }}")
                }
                SimulationState.Finished -> {
                    println("Simulation Finished")
                }

                else -> {}
            }

            // Delay of 8 seconds before processing the next state
            delay(3.seconds)
        }
    }
}
