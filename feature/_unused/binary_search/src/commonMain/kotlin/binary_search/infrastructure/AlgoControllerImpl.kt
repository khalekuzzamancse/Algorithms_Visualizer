package binary_search.infrastructure

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import binary_search.PackageLevelAccess
import binary_search.domain.AlgoStateController
import binary_search.domain.BaseIterator
import binary_search.domain.VisualizationState

/**
 * @param iterator used as abstract so that we can provide different implementation
 * or can change the iterator easily
 */
@PackageLevelAccess //avoid to access other layer such ui layer
internal class AlgoControllerImpl<T : Comparable<T>>(
    private val iterator: BaseIterator<T>
) : AlgoStateController<T> {
    private val sequence = iterator.result.iterator()
    override val pseudocode = iterator.pseudocode
    private val _state = MutableStateFlow<VisualizationState>(iterator.initialState())
    override val algoState: StateFlow<VisualizationState> = _state.asStateFlow()


    override fun next() {
        if (sequence.hasNext()) {
            val res = sequence.next()
            _state.update { res }
        }

    }

    override fun hasNext() = sequence.hasNext()



}