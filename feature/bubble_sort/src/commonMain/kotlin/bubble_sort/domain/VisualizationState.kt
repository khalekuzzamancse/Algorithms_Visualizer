package bubble_sort.domain

internal interface VisualizationState{
     data class AlgoState<T>(
         val i: Int?,
         val j: Int?,
         val swappablePair: SwappedPair<T>?,
    ): VisualizationState

    data object Finished : VisualizationState

}


/**
 * Using this to reduce the responsible of client to manually calculating the value based on index
 * Though it seems reduce but it is helpful
 */
internal data class SwappedPair<T>(
    val j: Int,
    val jPlus1: Int,
    val jValue: T,
    val jPlus1Value: T
)




