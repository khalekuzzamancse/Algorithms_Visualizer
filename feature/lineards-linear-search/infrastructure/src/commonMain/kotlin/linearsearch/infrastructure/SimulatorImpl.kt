package linearsearch.infrastructure

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import linearsearch.domain.model.DataModel
import linearsearch.domain.model.SimulationState
import linearsearch.domain.service.PseudocodeGenerator
import linearsearch.domain.service.Simulator

class SimulatorImpl<T : Comparable<T>> internal constructor(
    model: DataModel<T>,
    enablePseudocode: Boolean
) : Simulator<T> {

    private val iterator: kotlin.collections.Iterator<SimulationState>


    init {
        val sequence = Iterator(model.array, model.target)
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
