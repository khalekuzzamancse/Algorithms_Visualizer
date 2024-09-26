package infrastructure.factory

import domain.model.NodeModel
import domain.model.SimulationState
import domain.service.Graph
import domain.service.Simulator

class SimulatorImpl internal  constructor(
    graph: Graph,
    startNode: NodeModel
) : Simulator {

    private val iterator: Iterator<SimulationState>

    init {
        // Initialize the sequence and get its iterator
        val dijkstraSequence = DijkstraSimulation().runDijkstra(graph as GraphImpl, startNode)
        iterator = dijkstraSequence.iterator()
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
            SimulationState.Finished // Return a finished state when there are no more steps
        }
    }
}
