package binarysearch.domain.service

import binarysearch.domain.model.SimulationState

interface Simulator<T> {
    fun next(): SimulationState
}