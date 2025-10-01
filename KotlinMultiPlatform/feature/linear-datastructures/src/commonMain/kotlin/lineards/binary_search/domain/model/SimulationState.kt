@file:Suppress("propertyName")

package lineards.binary_search.domain.model

sealed interface SimulationState {
    val code: String
    data class Start( override val code: String) : SimulationState
    data class PointerLow(val index: Int,override val code: String) : SimulationState
    data class PointerHigh(val index: Int,override val code: String) : SimulationState
    data class PointerMid(val index: Int,override val code: String) : SimulationState

    /**
     * - When mid need to recreate or destroyed
     */
    data class ClearMid(override val code: String) : SimulationState
    data class FoundAt(val index: Int,override val code: String): SimulationState
    data class Finished(override val code: String) : SimulationState

}