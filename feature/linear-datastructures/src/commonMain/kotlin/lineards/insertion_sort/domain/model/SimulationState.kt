@file:Suppress("propertyName")

package lineards.insertion_sort.domain.model

sealed interface SimulationState {
    val code:String
    /**Represent the pointer `i`*/
    data class BackwardPointerChanged(val j: Int, val `j-1`: Int, override val code: String) :
        SimulationState

    /**Clear the minIndex variable when it go out of scope*/
    data  class ClearBackwardPointer(override val code: String) : SimulationState

    /**Represent the pointer `j`*/
    data class PointerIChanged(val index: Int,val sortedUpTo:Int,override val code: String) :
        SimulationState
    data class Swap(val i: Int, val j: Int,override val code: String) : SimulationState

    data class Finished(override val code: String) : SimulationState

}