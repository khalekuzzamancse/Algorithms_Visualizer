package bubblesort.domain.model

sealed interface SimulationState {
    /**Represent the pointer `i`*/
    data class PointerIChanged(val index: Int) : SimulationState
    /**Represent the pointer `j`*/
    data class PointerJChanged(val index: Int) : SimulationState
    data class Swap(val i: Int, val j: Int) : SimulationState
    data object Finished : SimulationState

}