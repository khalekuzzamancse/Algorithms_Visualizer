package graphtraversal.infrastructure.factory

import graphtraversal.domain.model.GraphModel
import graphtraversal.domain.model.SimulationState
import graphtraversal.domain.service.Simulator
import graphtraversal.infrastructure.services.GraphImpl


class SimulatorImpl internal constructor(
    graphModel: GraphModel
) : Simulator {

    private val iterator: Iterator<SimulationState>


    init {
        val sequence = DFSSimulation(graph = GraphImpl(graphModel)).start()
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
            SimulationState.Finished
        }
    }
}
