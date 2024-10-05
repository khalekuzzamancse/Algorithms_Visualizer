package graphtopologicalsort.domain.service

import graphtopologicalsort.domain.model.SimulationState

interface Simulator {
    fun next(): SimulationState
}