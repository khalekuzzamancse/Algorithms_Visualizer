package graph.topological_sort.inf

import graph._core.domain.GraphModel
import graph._core.inf.GraphImpl
import graph.topological_sort.domain.TopologicalCodeGenerator
import graph.topological_sort.domain.TopologicalSortSimulator
import graph.topological_sort.domain.TopologicalSortState


class TopologicalSortSimulatorImpl internal constructor(
    graphModel: GraphModel
) : TopologicalSortSimulator {


    private val iterator: kotlin.collections.Iterator<TopologicalSortState>


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
    override fun next(): TopologicalSortState {
        return if (iterator.hasNext()) {
            iterator.next()
        } else {
            TopologicalSortState.Finished(TopologicalCodeGenerator.generate())
        }
    }
}
