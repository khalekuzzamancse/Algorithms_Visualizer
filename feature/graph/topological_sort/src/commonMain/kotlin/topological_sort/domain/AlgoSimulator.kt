package topological_sort.domain

import kotlinx.coroutines.flow.StateFlow

/**
 * - Contain the method for simulating the algo
 * - Contain the state of the algo at current point of time
 * - Contain the Pseudocode of the algo with proper highlight
 */
internal interface AlgoSimulator {

    /**
     * - Need not to state be observable,because we  are using iterator and only changing state upon clicking on next,that means we are pulling the state,
     * so need not to make it observable,because we are not pushing the state (until it demanded)
     */
    fun next(): SimulationState
}