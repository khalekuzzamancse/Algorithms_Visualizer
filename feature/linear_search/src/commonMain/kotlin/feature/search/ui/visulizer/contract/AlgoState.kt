package feature.search.ui.visulizer.contract


internal data class AlgoState<T>(
    val target: T,
    val currentIndex: Int?,
    val currentElement: T?,
)


