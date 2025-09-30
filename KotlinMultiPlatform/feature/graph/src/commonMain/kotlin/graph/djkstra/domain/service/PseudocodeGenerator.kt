package graph.djkstra.domain.service

import graph.djkstra.domain.model.CodeStateModel

object PseudocodeGenerator {
    fun generate(model: CodeStateModel) = with(model) {
        """
    Dijkstra(graph, source) {
       pq ${pq.showState()}
      initializeDistance()
      initializePriorityQueue(source) 
        
        while (pq.isNotEmpty()) {${queueIsEmpty.showState()}
            current = pq.poll() ${current.showState()}
            markAsProcessed(current)
            neighbours = graph.getUnProcessedNeighbourOf(current) ${unProcessedNeighbours.showState()}
            updatedDistanceVia(current, neighbours)
            pq.addAll(neighbours)
        }
    }
""".trimIndent()
    }

    private fun String?.showState() = this?.let { "//$it" } ?: ""

}
