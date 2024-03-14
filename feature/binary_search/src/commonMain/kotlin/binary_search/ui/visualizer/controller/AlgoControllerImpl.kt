package binary_search.ui.visualizer.controller

import androidx.compose.runtime.Composable
import binary_search.ui.visualizer.contract.AlgoState
import binary_search.ui.visualizer.contract.AlgoStateController
import binary_search.ui.visualizer.contract.Pseudocode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


internal class AlgoControllerImpl<T : Comparable<T>>(list: List<T>, private val target: T) :
    AlgoStateController<T> {
    private val builder = Sequences(list, target)
    private val iterator = builder.result.iterator()
    override val pseudocode = MutableStateFlow(emptyList<Pseudocode.Line>())
    private val _state = MutableStateFlow(initializeState())
    override val algoState: StateFlow<AlgoState<T>> = _state.asStateFlow()


    override fun next() {
        if (iterator.hasNext()) {
            val res = iterator.next()

            _state.update { res }
        }

    }

    override fun hasNext() = iterator.hasNext()
    private fun initializeState(): AlgoState<T> =
        AlgoState(
            target = target,
            low = null,
            high = null,
            mid = null,
            searchEnded = false,
            isMatched = null,
            currentElement = null
        )


}

internal class Sequences<T : Comparable<T>>(
    private val list: List<T>,
    private val target: T,
) {
    private var low = 0
    private var high = list.size - 1
    private var searchEnded = false
    private var isMatched: Boolean? = null
    private var currentElement: T? = null

    val result = sequence {
        yield(newState()) // Initial state

        while (low <= high) {
            val mid = (low + high) / 2
            currentElement = list[mid]
            isMatched = currentElement == target
            yield(newState(mid))
            if (isMatched as Boolean) {
                searchEnded = true
                yield(newState(mid)) // Target found
                break
            } else if (currentElement!! < target) {
                low = mid + 1
            } else {
                high = mid - 1

            }
            yield(newState())
        }

        if (!searchEnded) { // If the search ends without finding the target
            searchEnded = true
            yield(newState())
        }
    }

    private fun newState(mid: Int? = null): AlgoState<T> {
        return AlgoState(
            target = target,
            low = low,
            high = high,
            mid = mid,
            searchEnded = searchEnded,
            isMatched = isMatched,
            currentElement = if (mid == null) null else currentElement,

            )
    }
}





