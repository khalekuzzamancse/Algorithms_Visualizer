package graph.djkstra.domain.service

import graph.djkstra.domain.model.SimulationState

interface Simulator {
    fun next(): SimulationState
}