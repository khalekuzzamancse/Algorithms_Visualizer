package mst.infrastructure.factory

import mst.domain.model.SimulationState
import mst.domain.service.CodeStateModel
import mst.domain.service.PseudocodeGenerator

/**
 * - It is prime algorithm
 */
class Iterator(
    private val graph: GraphImpl
) {
    private var model=CodeStateModel()
    private fun  CodeStateModel.toCode()=PseudocodeGenerator.generate(this)
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
        model=model.copy(pendingNodes = "$notAddedToMST", source = startNodeId)

        yield(SimulationState.Misc(model.toCode()))

        while (notAddedToMST.isNotEmpty()) {
            model=model.copy(pendingNodes = "$notAddedToMST", pendingIsNotEmpty = "true")
            val processingId = notAddedToMST.minByOrNull { graph.getDistanceOf(it) ?: Int.MAX_VALUE } ?: break
            notAddedToMST.remove(processingId) //make sense to remove it since this is processing,actually pulling from priority queue
            model=model.copy(pendingNodes = "$notAddedToMST", processing = "$processingId")
            graph.getParent(processingId)?.let { parentNode ->
                graph.findEdge(processingId, parentNode.id)?.let { edge ->
                    yield(SimulationState.ProcessingEdge(edge,model.toCode()))
                }
            }

            yield(SimulationState.ProcessingNode(graph.getNode(processingId)!!,model.toCode()))


            val pendingNeighbours=  graph
                .getNeighborsOf(processingId)
                .filter { neighborInfo -> neighborInfo.nodeId in notAddedToMST }

            model=model.copy(pendingNeighbours="${pendingNeighbours.map { it.nodeId }}")
            yield(SimulationState.Misc(model.toCode()))//Just for show the pending neighbour in pseudocode

           pendingNeighbours
                .forEach { neighborInfo ->
                    val neighborId = neighborInfo.nodeId
                    val edgeCost = neighborInfo.edgeCost

                    if (graph.getDistanceOf(neighborId)!! > edgeCost) {
                        graph.updateDistanceOf(neighborId, edgeCost)
                        graph.updateParentOf(neighborId, processingId)
                    }
                }

            model=model.copy(pendingNodes = "$notAddedToMST").killProcessing().killPendingNeighbours()
        }
        model=model.copy(pendingNodes = "[]", pendingIsNotEmpty = "false")
        yield(SimulationState.Finished(model.toCode()))
    }
}
