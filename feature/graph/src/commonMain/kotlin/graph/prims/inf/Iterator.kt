package graph.prims.inf

import graph.prims.domain.model.DijstraSimulationState
import graph.prims.domain.service.DijkstraCodeStateModel
import graph.prims.domain.service.DijkstraPseudocodeGenerator


/**
 * - It is prime algorithm
 */
class Iterator(
    private val graph: DijkstraGraphImpl
) {
    private var model= DijkstraCodeStateModel()
    private fun DijkstraCodeStateModel.toCode()= DijkstraPseudocodeGenerator.generate(this)
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

        yield(DijstraSimulationState.Misc(model.toCode()))

        while (notAddedToMST.isNotEmpty()) {
            model=model.copy(pendingNodes = "$notAddedToMST", pendingIsNotEmpty = "true")
            val processingId = notAddedToMST.minByOrNull { graph.getDistanceOf(it) ?: Int.MAX_VALUE } ?: break
            notAddedToMST.remove(processingId) //make sense to remove it since this is processing,actually pulling from priority queue
            model=model.copy(pendingNodes = "$notAddedToMST", processing = "$processingId")
            graph.getParent(processingId)?.let { parentNode ->
                graph.findEdge(processingId, parentNode.id)?.let { edge ->
                    yield(DijstraSimulationState.ProcessingEdge(edge,model.toCode()))
                }
            }

            yield(DijstraSimulationState.ProcessingNode(graph.getNode(processingId)!!,model.toCode()))


            val pendingNeighbours=  graph
                .getNeighborsOf(processingId)
                .filter { neighborInfo -> neighborInfo.nodeId in notAddedToMST }

            model=model.copy(pendingNeighbours="${pendingNeighbours.map { it.nodeId }}")
            yield(DijstraSimulationState.Misc(model.toCode()))//Just for show the pending neighbour in pseudocode

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
        yield(DijstraSimulationState.Finished(model.toCode()))
    }
}
