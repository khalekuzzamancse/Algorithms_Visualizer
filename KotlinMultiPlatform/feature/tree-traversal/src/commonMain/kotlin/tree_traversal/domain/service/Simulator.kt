package tree_traversal.domain.service

import tree_traversal.domain.model.SimulationState

interface Simulator {
    fun next(): SimulationState
}