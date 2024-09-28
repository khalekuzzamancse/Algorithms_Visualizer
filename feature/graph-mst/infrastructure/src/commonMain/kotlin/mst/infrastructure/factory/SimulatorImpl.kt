package mst.infrastructure.factory

import mst.domain.model.EdgeModel
import mst.domain.model.NodeModel
import mst.domain.model.SimulationState
import mst.domain.service.Graph
import mst.domain.service.Simulator

class SimulatorImpl internal  constructor(
    nodes: Set<NodeModel>,
    edges: Set<EdgeModel>,
    startNode: NodeModel
) : Simulator {

    private val iterator: Iterator<SimulationState>


    init {
        val sequence = PrimSimulation(graph = Graph2(nodes,edges,startNode)).start()
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
            SimulationState.Finished // Return a finished state when there are no more steps
        }
    }
}
