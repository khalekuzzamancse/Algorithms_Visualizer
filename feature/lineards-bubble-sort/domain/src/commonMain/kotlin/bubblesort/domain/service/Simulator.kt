package bubblesort.domain.service

import bubblesort.domain.model.SimulationState

interface Simulator<T> {
    fun next(): SimulationState
}