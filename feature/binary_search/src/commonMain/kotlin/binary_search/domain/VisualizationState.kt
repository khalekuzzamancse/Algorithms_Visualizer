package binary_search.domain

interface VisualizationState{
     data class AlgoState<T>(
         val target: T,
         val low: Int?,
         val high:Int?,
         val mid:Int?,
         val midValue: T?,
    ): VisualizationState
    data class Finished(
        val low: Int?,
        val high:Int?,
        val mid: Int?,
    ): VisualizationState

}


