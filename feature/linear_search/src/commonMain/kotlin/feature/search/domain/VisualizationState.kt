package feature.search.domain

interface VisualizationState{
     data class AlgoState<T>(
        val target: T,
        val currentIndex: Int?,
        val currentElement: T?,
    ): VisualizationState
    data class Finished(
        val foundedIndex: Int,
        val comparisons:Int
    ): VisualizationState

}


