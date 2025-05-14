package lineards.linear_search.domain.service

import core.ui.CoreSimulator
import lineards.linear_search.domain.model.SimulationState

interface Simulator<T>:CoreSimulator {
    fun next(): SimulationState
}