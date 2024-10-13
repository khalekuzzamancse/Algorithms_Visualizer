package bubblesort.infrastructure

import bubblesort.domain.model.DataModel
import bubblesort.domain.model.SimulationState
import bubblesort.domain.service.PseudocodeGenerator
import bubblesort.domain.service.Simulator

class SimulatorImpl<T : Comparable<T>> internal constructor(model: DataModel<T>) : Simulator<T> {

    private val iterator: kotlin.collections.Iterator<SimulationState>


    init {
        val sequence = Iterator(model.array)
        iterator = sequence.start().iterator()
    }

    /**
     * Moves to the next state in the simulation by delegating to the sequence iterator.
     *
     * @return The next [SimulationState] in the simulation.
     * @throws NoSuchElementException if the simulation has finished and there are no more states.
     */
    override fun next(): SimulationState = if (iterator.hasNext()) iterator.next()
    else SimulationState.Finished(PseudocodeGenerator.rawCode)

}
