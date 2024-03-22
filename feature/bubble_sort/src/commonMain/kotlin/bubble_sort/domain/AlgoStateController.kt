package bubble_sort.domain
import kotlinx.coroutines.flow.StateFlow

internal interface AlgoStateController<T> {
    val pseudocode: StateFlow<List<Pseudocode.Line>>
    val algoState: StateFlow<VisualizationState>
    val sortedList:List<T>
    fun next()
    fun hasNext(): Boolean
}