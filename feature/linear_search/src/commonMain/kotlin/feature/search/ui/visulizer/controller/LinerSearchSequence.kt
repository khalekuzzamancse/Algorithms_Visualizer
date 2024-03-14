package feature.search.ui.visulizer.controller

import feature.search.ui.visulizer.model.LinearSearcher
import feature.search.ui.visulizer.model.PseudoCodeLine
import feature.search.ui.visulizer.model.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class LinearSearchSequence<T>(list: List<T>, target: T) : LinearSearcher<T> {
 private   val iterator = Sequences(list, target).result.iterator()

    override val pseudocode: StateFlow<List<PseudoCodeLine>> = MutableStateFlow(emptyList())
    private val _state = MutableStateFlow(initializeState())
    override val state: StateFlow<State<T>> = _state.asStateFlow()


    override fun next() {
        if (iterator.hasNext()) {
            val res = iterator.next()
            println(res)
            _state.update { res }
        }

    }

    override fun hasNext() = iterator.hasNext()
    private fun initializeState(): State<T> =
        State(-1, false, null, null)


}

private class Sequences<T>(
    private val list: List<T>,
    private val target: T,
) {
    private var currentIndex = -1 // Before search starts
    private var searchEnded = false
    private var isMatched: Boolean? = null
    private var currentElement: T? = null

    val result = sequence {
        yield(newState()) // Search not started
        for (i in list.indices) {
            currentIndex = i
            currentElement = list[i]
            isMatched = currentElement == target
            yield(newState())
            if (isMatched as Boolean) {
                searchEnded = true
                yield(newState()) // Target found
                break // Exit after finding the match
            }
        }

        if (!searchEnded) { // If the search ends without finding the target
            currentIndex = -1 // Reset index to indicate search end without success
            searchEnded = true
            yield(newState())
        }
    }

    private fun newState() = State(currentIndex, searchEnded, isMatched, currentElement)

}




