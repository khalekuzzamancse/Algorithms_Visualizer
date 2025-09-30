package lineards.bubble_sort.domain.model

sealed interface SimulationState {
    val code: String
    /**Represent the pointer `i`*/
    data class Start( override val code: String) : SimulationState
    data class PointerIChanged(val index: Int, override val code: String) : SimulationState
    /**Represent the pointer `j`*/
    data class PointerJChanged(val index: Int, override val code: String) : SimulationState
    data class Swap(val i: Int, val j: Int, override val code: String) : SimulationState
    data class Finished( override val code: String) : SimulationState

}