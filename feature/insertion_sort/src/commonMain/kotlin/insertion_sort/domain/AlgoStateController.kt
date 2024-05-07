package insertion_sort.domain
import kotlinx.coroutines.flow.StateFlow

internal interface AlgoStateController<T> {
    val pseudocode: StateFlow<List<LineForPseudocode>>
    val algoState: StateFlow<AlgoState<T>>
    fun next()
    fun hasNext(): Boolean
}