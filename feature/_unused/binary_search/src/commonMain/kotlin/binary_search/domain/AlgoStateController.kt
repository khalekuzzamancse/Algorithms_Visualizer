package binary_search.domain
import kotlinx.coroutines.flow.StateFlow

/**
 * It define the abstract controller so that the underlying implementation
 * can changed,such as we can change the underlying library or the design pattern
 * in future for some reason
 *
 */
internal interface AlgoStateController<T> {
    val pseudocode: StateFlow<List<Pseudocode.Line>>
    val algoState: StateFlow<VisualizationState>
    fun next()
    fun hasNext(): Boolean
}