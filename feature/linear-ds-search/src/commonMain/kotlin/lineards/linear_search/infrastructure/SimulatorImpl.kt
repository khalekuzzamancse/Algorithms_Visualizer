package lineards.linear_search.infrastructure

import lineards.linear_search.domain.model.DataModel
import lineards.linear_search.domain.model.SimulationState
import lineards.linear_search.domain.service.PseudocodeGenerator
import lineards.linear_search.domain.service.Simulator

class SimulatorImpl<T : Comparable<T>> internal constructor(
    model: DataModel<T>,
    enablePseudocode: Boolean
) : Simulator<T> {

    private val iterator: kotlin.collections.Iterator<SimulationState>
    init {
        val sequence = Iterator(model.array, model.target)
        iterator = sequence.start().iterator()


    }

    override fun isFinished()=!iterator.hasNext()


    /**
     * Moves to the next state in the simulation by delegating to the sequence iterator.
     *
     * @return The next [SimulationState] in the simulation.
     * @throws NoSuchElementException if the simulation has finished and there are no more states.
     */
    override fun next(): SimulationState = if (iterator.hasNext()) iterator.next()
    else SimulationState.Finished(PseudocodeGenerator.rawCode)

}
