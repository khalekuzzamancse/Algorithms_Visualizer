package infrastructure.factory

import domain.model.EdgeModel
import domain.model.NodeModel
import domain.model.SimulationState
import java.util.PriorityQueue
import kotlin.sequences.sequence

class DijkstraSimulation internal constructor() {

    fun runDijkstra(graph: GraphImpl, startNode: NodeModel) = sequence<SimulationState> {
        // Initialize distances
        initializeDistances(graph, startNode)
        yield(SimulationState.DistanceUpdated(graph.getNodes()))
        // Priority queue for processing nodes, with the smallest distance at the head
        val priorityQueue = PriorityQueue<NodeModel>(compareBy { it.distance })
        priorityQueue.add(startNode)
        val visited = mutableSetOf<NodeModel>() // Track visited nodes

        // Process each node in the queue
        while (priorityQueue.isNotEmpty()) {
            val currentNode = priorityQueue.poll()
            visited.add(currentNode)

            // Yield the state when processing a node
            yield(SimulationState.ProcessingNode(currentNode))

            // If the current node is unreachable, skip it
            if (currentNode.distance == NodeModel.INFINITY) continue

            // Get neighbors and their weights from the graph
            graph.getNeighborsWithWeights(currentNode).forEach { neighborInfo ->
                val neighbor = neighborInfo.to
                val edgeCost = neighborInfo.weight

                val currentEdge = EdgeModel(
                    id =neighborInfo.edgeId ,
                    u=currentNode,
                   v= neighbor,
                   cost =  edgeCost
                )

                // Yield the state when processing an edge
                yield(SimulationState.ProcessingEdge(currentEdge))

                if (!visited.contains(neighbor)) {
                    // Calculate new distance to neighbor
                    val newDistance = currentNode.distance + edgeCost

                    // If the new distance is smaller, update the neighbor's distance
                    if (newDistance < neighbor.distance) {
                        priorityQueue.remove(neighbor) // Remove from queue to update
                        neighbor.distance = newDistance // Update distance

                        // Yield the state when the distance is updated
                        yield(SimulationState.DistanceUpdated(graph.getNodes()))

                        priorityQueue.add(neighbor)    // Re-add to queue with updated distance
                    }
                }
            }
        }
        // Yield the finished state when done
        yield(SimulationState.Finished)
    }

    // Initialize distances for all nodes in the graph
    private fun initializeDistances(graph: GraphImpl, startNode: NodeModel) {
        graph.getNodes().forEach { node ->
            node.distance = NodeModel.INFINITY
        }
        startNode.distance = 0 // Starting node gets distance 0
    }
}
