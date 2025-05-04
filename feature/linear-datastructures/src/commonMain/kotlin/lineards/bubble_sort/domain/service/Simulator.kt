package bubblesort.domain.service

import lineards.bubble_sort.domain.model.SimulationState

interface Simulator<T> {
    fun next(): SimulationState
}