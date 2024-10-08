package bubble_sort.infrastructure

import bubble_sort.domain.AlgoStateController
import bubble_sort.domain.BaseIterator
import bubble_sort.domain.VisualizationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


internal class AlgoControllerImpl<T : Comparable<T>>(private val iterator: BaseIterator<T>) :
    AlgoStateController<T> {

    private val sequence = iterator.result.iterator()
    override val pseudocode = iterator.pseudocode
    private val _state = MutableStateFlow<VisualizationState>(initializeState())
    override val algoState: StateFlow<VisualizationState> = _state.asStateFlow()
    override val sortedList: List<T> = iterator.sortedList


    override fun next() {
        if (sequence.hasNext()) {
            val res = sequence.next()
            _state.update { res }
        }

    }

    override fun hasNext() = sequence.hasNext()
    private fun initializeState(): VisualizationState.AlgoState<T> {
        return VisualizationState.AlgoState(
            i = null,
            j = null,
            swappablePair = null,
        )
    }

}