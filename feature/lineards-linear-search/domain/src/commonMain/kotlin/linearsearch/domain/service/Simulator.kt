package linearsearch.domain.service

import kotlinx.coroutines.flow.StateFlow
import linearsearch.domain.model.SimulationState

interface Simulator<T> {
    fun next(): SimulationState
}