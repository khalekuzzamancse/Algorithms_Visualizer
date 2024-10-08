package insertionsort.domain.service

import insertionsort.domain.model.SimulationState

interface Simulator<T> {
    fun next(): SimulationState
}