package graph.prims.inf

import graph._core.domain.DomainNodeModel
import graph._core.domain.EdgeModel
import graph.prims.domain.model.PrimsSimulationState
import graph.prims.domain.service.PrimsCodeGenerator
import graph.prims.domain.service.PrimsCodeStateModel
import graph.prims.domain.service.PrimsSimulator

class PrimsSimulatorImpl internal  constructor(
    nodes: Set<DomainNodeModel>,
    edges: Set<EdgeModel>,
    startNode: DomainNodeModel
) : PrimsSimulator {

    private val iterator: kotlin.collections.Iterator<PrimsSimulationState>


    init {
        val sequence = Iterator(graph = PrimsGraphImpl(nodes,edges,startNode))
            .start()
        iterator = sequence.iterator()
    }

    /**
     * Moves to the next state in the simulation by delegating to the sequence iterator.
     *
     * @return The next [PrimsSimulationState] in the simulation.
     * @throws NoSuchElementException if the simulation has finished and there are no more states.
     */
    override fun next(): PrimsSimulationState {
        return if (iterator.hasNext()) {
            iterator.next()
        } else {
            PrimsSimulationState.Finished(PrimsCodeGenerator.generate(
                PrimsCodeStateModel()
            )) // Return a finished state when there are no more steps
        }
    }
}
