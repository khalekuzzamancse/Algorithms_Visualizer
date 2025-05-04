package lineards.linear_search.domain.service

import lineards.linear_search.domain.model.SimulationState

interface Simulator<T> {
     fun  isFinished():Boolean
    fun next(): SimulationState
}