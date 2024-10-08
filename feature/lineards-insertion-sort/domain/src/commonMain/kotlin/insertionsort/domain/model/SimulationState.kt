@file:Suppress("propertyName")

package insertionsort.domain.model

sealed interface SimulationState {
    /**Represent the pointer `i`*/
    data class BackwardPointerChanged(val j: Int, val `j-1`: Int) : SimulationState

    /**Clear the minIndex variable when it go out of scope*/
    data object ClearBackwardPointer : SimulationState

    /**Represent the pointer `j`*/
    data class PointerIChanged(val index: Int,val sortedUpTo:Int) : SimulationState
    data class Swap(val i: Int, val j: Int) : SimulationState

    data object Finished : SimulationState

}