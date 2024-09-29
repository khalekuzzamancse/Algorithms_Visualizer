package tree.domain.service

import tree.domain.model.SimulationState

interface Simulator {
    fun next(): SimulationState
}