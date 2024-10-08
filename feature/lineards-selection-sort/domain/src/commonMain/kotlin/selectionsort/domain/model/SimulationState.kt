package selectionsort.domain.model

sealed interface SimulationState {
    /**Represent the pointer `i`*/
    data class PointerMinIndexChanged(val index: Int) : SimulationState
    /**Clear the minIndex variable when it go out of scope*/
    data object ClearMinIndex:SimulationState
    /**Represent the pointer `j`*/
    data class PointerIChanged(val index: Int) : SimulationState
    data class Swap(val i: Int, val j: Int) : SimulationState

    data object Finished : SimulationState

}