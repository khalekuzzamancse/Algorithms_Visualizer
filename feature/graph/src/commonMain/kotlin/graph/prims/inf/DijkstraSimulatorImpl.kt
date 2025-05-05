package graph.prims.inf

import graph._core.domain.DomainNodeModel
import graph._core.domain.EdgeModel
import graph.prims.domain.model.DijstraSimulationState
import graph.prims.domain.service.DijkstraCodeStateModel
import graph.prims.domain.service.DijkstraPseudocodeGenerator
import graph.prims.domain.service.DijkstraSimulator

class DijkstraSimulatorImpl internal  constructor(
    nodes: Set<DomainNodeModel>,
    edges: Set<EdgeModel>,
    startNode: DomainNodeModel
) : DijkstraSimulator {

    private val iterator: kotlin.collections.Iterator<DijstraSimulationState>


    init {
        val sequence = Iterator(graph = DijkstraGraphImpl(nodes,edges,startNode))
            .start()
        iterator = sequence.iterator()
    }

    /**
     * Moves to the next state in the simulation by delegating to the sequence iterator.
     *
     * @return The next [DijstraSimulationState] in the simulation.
     * @throws NoSuchElementException if the simulation has finished and there are no more states.
     */
    override fun next(): DijstraSimulationState {
        return if (iterator.hasNext()) {
            iterator.next()
        } else {
            DijstraSimulationState.Finished(DijkstraPseudocodeGenerator.generate(
                DijkstraCodeStateModel()
            )) // Return a finished state when there are no more steps
        }
    }
}
