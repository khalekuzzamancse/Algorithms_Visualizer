package mst.domain.service

import mst.domain.model.SimulationState

interface Simulator {
    fun next(): SimulationState
}