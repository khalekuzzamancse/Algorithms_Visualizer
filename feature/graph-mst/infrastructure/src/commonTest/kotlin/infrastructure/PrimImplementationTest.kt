package infrastructure

import mst.domain.model.EdgeModel
import mst.domain.model.NodeModel
import mst.domain.model.SimulationState
import mst.infrastructure.factory.GraphImpl
import mst.infrastructure.factory.PrimSimulation
import kotlin.test.Test
import kotlin.test.fail

class PrimImplementationTest {
    @Test
    fun test() {
        // Define nodes
        val nodeA = NodeModel(id = "A", label = "A")
        val nodeB = NodeModel(id = "B", label = "B")
        val nodeC = NodeModel(id = "C", label = "C")
        val nodeD = NodeModel(id = "D", label = "D")
        val nodeE = NodeModel(id = "E", label = "E")
        val nodeF = NodeModel(id = "F", label = "F")

        // Define edges with weights for the undirected graph
        val edges = setOf(
            EdgeModel(id = "1", u = nodeA, v = nodeB, cost = 6),
            EdgeModel(id = "2", u = nodeA, v = nodeC, cost = 9),  // A-C with weight 9
            EdgeModel(id = "3", u = nodeA, v = nodeD, cost = 4),
            EdgeModel(id = "4", u = nodeB, v = nodeC, cost = 3),
            EdgeModel(id = "5", u = nodeB, v = nodeE, cost = 10),
            EdgeModel(id = "6", u = nodeC, v = nodeD, cost = 8),
            EdgeModel(id = "7", u = nodeC, v = nodeF, cost = 5),
            EdgeModel(id = "8", u = nodeC, v = nodeE, cost = 7),
            EdgeModel(id = "9", u = nodeD, v = nodeF, cost = 2)
        )

        // Create the graph
        val graph = GraphImpl(
            nodes = setOf(nodeA, nodeB, nodeC, nodeD, nodeE, nodeF),
            edges = edges
        )

        // Run Prim's algorithm starting from node A
        PrimSimulation(graph, nodeA).start()
        fail()
        //when (state) {
//                is SimulationState.ProcessingNode -> println("Processing node: ${state.node.label}")
//                is SimulationState.ProcessingEdge -> println("Processing edge from ${state.edge.u.label} to ${state.edge.v.label} with cost ${state.edge.cost}")
//                is SimulationState.Finished -> println("Prim's Algorithm completed")
        //  }

    }
}
