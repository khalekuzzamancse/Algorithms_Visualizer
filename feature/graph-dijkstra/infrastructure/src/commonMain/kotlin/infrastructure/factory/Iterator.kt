@file:Suppress("functionName")

package infrastructure.factory

import domain.model.CodeStateModel
import domain.model.EdgeModel
import domain.model.NodeModel
import domain.model.SimulationState
import domain.service.PseudocodeGenerator
import java.util.PriorityQueue
import kotlin.sequences.sequence

class Iterator internal constructor() {
    private var model = CodeStateModel()
    private fun CodeStateModel._toCode() = PseudocodeGenerator.generate(this)

    fun runDijkstra(graph: GraphImpl, startNode: NodeModel) = sequence<SimulationState> {

        initializeDistances(graph, startNode)
        yield(SimulationState.DistanceUpdated(graph.getNodes(), code = model._toCode()))
        // Priority queue for processing nodes, with the smallest distance at the head
        val priorityQueue = PriorityQueue<NodeModel>(compareBy { it.distance })
        priorityQueue.add(startNode)

        val visited = mutableSetOf<NodeModel>()
        model = model.copy(pq = "${priorityQueue.map { "(${startNode.label},${startNode.distance})" }}")
        yield(SimulationState.Misc(model._toCode()))
        while (priorityQueue.isNotEmpty()) {
            model = model.copy(pq = "${priorityQueue.map { "(${it.label},${it.distance})" }}")

            yield(SimulationState.Misc(model._toCode()))//this is just for showing the updated queue

            val currentNode = priorityQueue.poll()
            visited.add(currentNode)
            model = model.copy(
                pq = "${priorityQueue.map { "(${it.label},${it.distance})" }}",
                current = currentNode.label,
                queueIsEmpty = "true"
            )
            yield(SimulationState.ProcessingNode(currentNode, code = model._toCode()))

            // If the current node is unreachable, skip it
            //   if (currentNode.distance == NodeModel.INFINITY) continue

            // Get neighbors and their weights from the graph
            model = model.copy(
                pq = "${priorityQueue.map { "(${it.label},${it.distance})" }}",
                current = currentNode.label,
                unProcessedNeighbours = graph.getNeighborsWithWeights(currentNode)
                    .filter { !visited.contains(it.to) }.map { it.to.label}.toString()
            )
            graph.getNeighborsWithWeights(currentNode).forEach { neighborInfo ->
                val neighbor = neighborInfo.to
                val edgeCost = neighborInfo.weight

                val currentEdge = EdgeModel(
                    id = neighborInfo.edgeId,
                    u = currentNode,
                    v = neighbor,
                    cost = edgeCost
                )

                // Yield the state when processing an edge
                yield(SimulationState.ProcessingEdge(currentEdge, code = model._toCode()))

                if (!visited.contains(neighbor)) {
                    // Calculate new distance to neighbor
                    val newDistance = currentNode.distance + edgeCost

                    // If the new distance is smaller, update the neighbor's distance
                    if (newDistance < neighbor.distance) {
                        priorityQueue.remove(neighbor) // Remove from queue to update
                        neighbor.distance = newDistance // Update distance


                        // Yield the state when the distance is updated
                        yield(SimulationState.DistanceUpdated(setOf(neighbor), code = model._toCode()))

                        priorityQueue.add(neighbor)    // Re-add to queue with updated distance
                    }
                }
            }

        }
        model=model.copy(queueIsEmpty = "false")
        // Yield the finished state when done
        yield(SimulationState.Finished(code = model._toCode()))
    }

    // Initialize distances for all nodes in the graph
    private fun initializeDistances(graph: GraphImpl, startNode: NodeModel) {
        graph.getNodes().forEach { node ->
            node.distance = NodeModel.INFINITY
        }
        startNode.distance = 0 // Starting node gets distance 0
    }
}
