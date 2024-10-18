package graphbfs.infrastructure.factory

import graphbfs.domain.model.GraphModel
import graphbfs.domain.model.SimulationState
import graphbfs.domain.service.PseudocodeGenerator
import graphbfs.domain.service.Simulator
import graphbfs.infrastructure.services.GraphImpl


class SimulatorImpl internal constructor(
    graphModel: GraphModel
) : Simulator {

    private val iterator: kotlin.collections.Iterator<SimulationState>


    init {
        val sequence = Iterator(graph = GraphImpl(graphModel)).start()
        iterator = sequence.iterator()
    }

    /**
     * Moves to the next state in the simulation by delegating to the sequence iterator.
     *
     * @return The next [SimulationState] in the simulation.
     * @throws NoSuchElementException if the simulation has finished and there are no more states.
     */
    override fun next(): SimulationState {
        return if (iterator.hasNext()) {
            iterator.next()
        } else {
            SimulationState.Finished(PseudocodeGenerator.rawCode)
        }
    }
}
