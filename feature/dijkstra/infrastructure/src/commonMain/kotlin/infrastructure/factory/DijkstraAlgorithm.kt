package infrastructure.factory

import domain.model.NodeModel
import java.util.PriorityQueue

class DijkstraAlgorithm internal  constructor(){

    fun runDijkstra(graph: GraphImpl, startNode: NodeModel) {
        // Initialize distances directly on nodes
        initializeDistances(graph, startNode)
        // Priority queue for processing nodes, with the smallest distance at the head
        val priorityQueue = PriorityQueue<NodeModel>(compareBy { it.distance })
        priorityQueue.add(startNode)
        val visited = mutableSetOf<NodeModel>() // Track visited nodes

        // Process each node in the queue
        while (priorityQueue.isNotEmpty()) {
            val currentNode = priorityQueue.poll()
            visited.add(currentNode)

            // If the current node is unreachable, skip it
            if (currentNode.distance == NodeModel.INFINITY) continue

            // Get neighbors and their weights from the graph
            graph.getNeighborsWithWeights(currentNode).forEach { neighborInfo ->
                val neighbor = neighborInfo.to
                val edgeCost = neighborInfo.weight
                if (!visited.contains(neighbor)) {
                    // Calculate new distance to neighbor
                    val newDistance = currentNode.distance + edgeCost
                    // If the new distance is smaller, update the neighbor's distance
                    if (newDistance < neighbor.distance) {
                        priorityQueue.remove(neighbor) // Remove from queue to update
                        neighbor.distance = newDistance // Update distance
                        priorityQueue.add(neighbor)    // Re-add to queue with updated distance
                    }
                }
            }
        }
    }

    // Initialize distances for all nodes in the graph
    private fun initializeDistances(graph: GraphImpl, startNode: NodeModel) {
        graph.getNodes().forEach { node ->
            node.distance = NodeModel.INFINITY
        }
        startNode.distance = 0 // Starting node gets distance 0
    }
}
