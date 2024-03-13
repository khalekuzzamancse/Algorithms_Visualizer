package feature.search.ui.visulizer.model

import kotlinx.coroutines.flow.StateFlow

internal interface LinearSearcher<T> {
    val pseudocode: StateFlow<List<PseudoCodeLine>>
    val state: StateFlow<State<T>>
    fun next()
    fun hasNext(): Boolean
}