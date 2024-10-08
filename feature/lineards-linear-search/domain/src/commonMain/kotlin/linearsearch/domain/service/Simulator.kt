package linearsearch.domain.service

import linearsearch.domain.model.SimulationState

interface Simulator<T> {
    fun next(): SimulationState
}