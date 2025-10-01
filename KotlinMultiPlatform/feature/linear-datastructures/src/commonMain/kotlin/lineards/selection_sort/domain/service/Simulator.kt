package lineards.selection_sort.domain.service

import lineards.selection_sort.domain.model.SimulationState

interface Simulator<T> {
    fun next(): SimulationState
}