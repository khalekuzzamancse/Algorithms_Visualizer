package lineards.insertion_sort.domain.service

import lineards.insertion_sort.domain.model.SimulationState

interface Simulator<T> {
    fun next(): SimulationState
}