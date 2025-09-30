package graph.prims.domain.service

import graph.prims.domain.model.PrimsSimulationState

interface PrimsSimulator {
    fun next(): PrimsSimulationState
}