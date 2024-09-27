package domain.service

import domain.model.SimulationState

interface Simulator {
    fun next(): SimulationState
}