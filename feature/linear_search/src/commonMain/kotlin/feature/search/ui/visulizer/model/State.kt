package feature.search.ui.visulizer.model


data class State<T>(
    val currentIndex: Int?,
    val searchEnded: Boolean,
    val isMatched: Boolean? = null,
    val currentElement: T?,
)