package infrastructure

import mst.domain.model.EdgeModel
import mst.domain.model.NodeModel
import mst.domain.model.SimulationState
import tree.infrastructure.factory.GraphImpl
import tree.infrastructure.factory.SimulatorImpl
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

        // Create an instance of the Dijkstra simulation
        val simulation = SimulatorImpl(
            graph = graph,
            startNode = nodeA,
        )
        while(true){
            when (val state=simulation.next()) {
                    is SimulationState.ProcessingNode -> {
                        println("Processing Node: ${state.node.label}")
                    }

                    is SimulationState.ProcessingEdge -> {
                        println("Processing Edge: ${state.edge}")
                    }


                    SimulationState.Finished -> {
                        println("Simulation Finished")
                        break

                    }

                    else -> {}
                }

                // Delay of 8 seconds before processing the next state
                delay(3.seconds)

        }}

}
