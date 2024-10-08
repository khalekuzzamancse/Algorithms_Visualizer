package selectionsort.domain.service

import selectionsort.domain.model.SimulationState

interface Simulator<T> {
    fun next(): SimulationState
}