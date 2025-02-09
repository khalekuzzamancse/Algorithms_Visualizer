package graphtopologicalsort.infrastructure.factory

import graphtopologicalsort.domain.model.GraphModel
import graphtopologicalsort.domain.model.SimulationState
import graphtopologicalsort.domain.service.PseudocodeGenerator
import graphtopologicalsort.domain.service.Simulator
import graphtopologicalsort.infrastructure.services.GraphImpl


class SimulatorImpl internal constructor(
    graphModel: GraphModel
) : Simulator {

    private val iterator: kotlin.collections.Iterator<SimulationState>


    init {
        val sequence = Iterator(graph = GraphImpl(graphModel))
            .start()
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
            SimulationState.Finished(PseudocodeGenerator.generate())
        }
    }
}
