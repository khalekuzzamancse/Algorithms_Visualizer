package feature.search.infrastructure

import feature.search.domain.AlgoStateController
import feature.search.domain.BaseIterator
import feature.search.domain.VisualizationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * @param iterator used as abstract so that we can provide different implementation
 * or can change the iterator easily
 */
internal class AlgoControllerImpl<T : Comparable<T>>(
    private val target: T,
    private val iterator: BaseIterator<T>
) : AlgoStateController<T> {
    //    private val builder = LinearSearchBaseIterator(list, target)
//    private val iterator = builder.result.iterator()

    private val sequence = iterator.result.iterator()
    override val pseudocode = iterator.pseudocode
    private val _state = MutableStateFlow<VisualizationState>(initializeState())
    override val algoState: StateFlow<VisualizationState> = _state.asStateFlow()


    override fun next() {
        if (sequence.hasNext()) {
            val res = sequence.next()
            _state.update { res }
        }

    }

    override fun hasNext() = sequence.hasNext()
    private fun initializeState(): VisualizationState.AlgoState<T> =
        VisualizationState.AlgoState(
            target = target,
            currentIndex = -1,
            currentElement = null
        )


}