package lineards.binary_search.domain.service

import lineards.binary_search.domain.model.SimulationState

interface Simulator<T> {
    fun isFinished():Boolean
    fun next(): SimulationState
}