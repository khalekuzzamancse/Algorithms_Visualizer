package binary_search.ui.visualizer.contract
import kotlinx.coroutines.flow.StateFlow

internal interface AlgoStateController<T> {
    val pseudocode: StateFlow<List<Pseudocode.Line>>
    val algoState: StateFlow<AlgoState<T>>
    fun next()
    fun hasNext(): Boolean
}