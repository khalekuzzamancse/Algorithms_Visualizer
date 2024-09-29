package tree.infrastructure.factory

import tree.domain.model.SimulationState

class PrimSimulation(
    private val graph: GraphImpl
) {

    private val notAddedToMST = mutableListOf<String>()

    init {
        val startNodeId = graph.startNodeId
        graph.updateDistanceOf(startNodeId, 0)
        notAddedToMST.addAll(graph.getAllNodeIds())
    }

    fun start() = sequence {
        val startNodeId = graph.startNodeId

        graph.getNeighborsOf(startNodeId).forEach {
            graph.updateDistanceOf(it.nodeId, it.edgeCost)
            graph.updateParentOf(it.nodeId, startNodeId)
        }

        while (notAddedToMST.isNotEmpty()) {
            val processingId = notAddedToMST.minByOrNull { graph.getDistanceOf(it) ?: Int.MAX_VALUE } ?: break
            graph.getParent(processingId)?.let { parentNode ->
                graph.findEdge(processingId, parentNode.id)?.let { edge ->
                    yield(SimulationState.ProcessingEdge(edge))
                }
            }

            yield(SimulationState.ProcessingNode(graph.getNode(processingId)!!))
            notAddedToMST.remove(processingId)

            graph.getNeighborsOf(processingId)
                .filter { neighborInfo -> neighborInfo.nodeId in notAddedToMST }
                .forEach { neighborInfo ->
                    val neighborId = neighborInfo.nodeId
                    val edgeCost = neighborInfo.edgeCost

                    if (graph.getDistanceOf(neighborId)!! > edgeCost) {
                        graph.updateDistanceOf(neighborId, edgeCost)
                        graph.updateParentOf(neighborId, processingId)
                    }
                }
        }

        yield(SimulationState.Finished)
    }
}
