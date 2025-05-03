@file:Suppress("propertyName")

package linearsearch.domain.model


sealed interface SimulationState {
    val code: String

    /** Represents the pointer `i` */
    data class Start( override val code: String) : SimulationState
    data class PointerI(val index: Int,   override val code: String) : SimulationState

    data class FoundAt(val index: Int, override val code: String) : SimulationState

    data class Finished(override val code: String) : SimulationState

}