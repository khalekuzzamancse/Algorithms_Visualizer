package graph.bfs.inf

import graph._core.domain.GraphModel
import graph._core.domain.TraversalSimulationState
import graph._core.domain.TraversalSimulator
import graph._core.inf.GraphImpl
import graph.bfs.domain.PseudocodeGenerator


class BFSBFSSimulatorImpl internal constructor(
    graphModel: GraphModel
) : TraversalSimulator {

    private val iterator: kotlin.collections.Iterator<TraversalSimulationState>


    init {
        val sequence = Iterator(graph = GraphImpl(graphModel)).start()
        iterator = sequence.iterator()
    }

    /**
     * Moves to the next state in the simulation by delegating to the sequence iterator.
     *
     * @return The next [BFSSimulationState] in the simulation.
     * @throws NoSuchElementException if the simulation has finished and there are no more states.
     */
    override fun next(): TraversalSimulationState {
        return if (iterator.hasNext()) {
            iterator.next()
        } else {
          TraversalSimulationState.Finished(PseudocodeGenerator.rawCode)
        }
    }
}
