package graphtraversal.domain.service

import graphtraversal.domain.model.SimulationState

interface Simulator {
    fun next(): SimulationState
}