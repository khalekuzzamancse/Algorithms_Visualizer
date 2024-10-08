@file:Suppress("propertyName")

package linearsearch.domain.model

sealed interface SimulationState {
    /**Represent the pointer `i`*/
    data class PointerI(val index: Int) : SimulationState
    data class FoundAt(val index: Int): SimulationState
    data object Finished : SimulationState

}