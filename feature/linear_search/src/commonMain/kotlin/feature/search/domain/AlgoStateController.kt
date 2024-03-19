package feature.search.domain
import kotlinx.coroutines.flow.StateFlow

internal interface AlgoStateController<T> {
    val pseudocode: StateFlow<List<Pseudocode.Line>>
    val algoState: StateFlow<VisualizationState>
    fun next()
    fun hasNext(): Boolean
}