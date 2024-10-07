package selection_sort.domain
import kotlinx.coroutines.flow.StateFlow

/**
 * - Contain the method for simulating the algo
 * - Contain the state of the algo at current point of time
 * - Contain the Pseudocode of the algo with proper highlight
 */
internal interface AlgoSimulator<T> {
    val pseudocode: StateFlow<List<LineForPseudocode>>
    val algoState: StateFlow<AlgoState<T>>
    fun next()
    fun hasNext(): Boolean
}