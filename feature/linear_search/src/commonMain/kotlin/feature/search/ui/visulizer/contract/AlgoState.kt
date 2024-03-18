package feature.search.ui.visulizer.contract

internal interface SimulationState{
     data class AlgoState<T>(
        val target: T,
        val currentIndex: Int?,
        val currentElement: T?,
    ):SimulationState
    data class Finished(
        val foundedIndex: Int,
        val comparisons:Int
    ):SimulationState

}


