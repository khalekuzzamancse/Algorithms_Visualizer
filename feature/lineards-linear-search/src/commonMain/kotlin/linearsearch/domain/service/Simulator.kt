package linearsearch.domain.service

import linearsearch.domain.model.SimulationState

interface Simulator<T> {
     fun  isFinished():Boolean
    fun next(): SimulationState
}