package graph.prims.domain.service

import graph.prims.domain.model.DijstraSimulationState

interface DijkstraSimulator {
    fun next(): DijstraSimulationState
}