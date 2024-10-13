package selectionsort.domain.model

sealed interface SimulationState {
    val code: String
    data class Start(override val code: String) : SimulationState
    /**Represent the pointer `i`*/
    data class PointerMinIndexChanged(val index: Int, override val code: String) : SimulationState

    /**Clear the minIndex variable when it go out of scope*/
    data class ClearMinIndex(override val code: String) : SimulationState

    /**Represent the pointer `j`*/
    data class PointerIChanged(val index: Int, override val code: String) : SimulationState
    data class Swap(val i: Int, val j: Int, override val code: String) : SimulationState
    data class Finished(override val code: String) : SimulationState

}