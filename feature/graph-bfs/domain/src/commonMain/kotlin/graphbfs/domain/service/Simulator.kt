package graphbfs.domain.service

import graphbfs.domain.model.SimulationState

interface Simulator {
    fun next(): SimulationState
}