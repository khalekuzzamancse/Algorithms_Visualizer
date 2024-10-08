@file:Suppress("propertyName")

package binarysearch.domain.model

sealed interface SimulationState {
    /**Represent the pointer `i`*/

    data class PointerLow(val index: Int) : SimulationState
    data class PointerHigh(val index: Int) : SimulationState
    data class PointerMid(val index: Int) : SimulationState

    /**
     * - When mid need to recreate or destroyed
     */
    data object ClearMid : SimulationState
    data class FoundAt(val index: Int): SimulationState
    data object Finished : SimulationState

}