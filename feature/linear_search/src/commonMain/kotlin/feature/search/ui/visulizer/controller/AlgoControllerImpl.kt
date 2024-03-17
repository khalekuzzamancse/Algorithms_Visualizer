package feature.search.ui.visulizer.controller

import feature.search.ui.visulizer.contract.AlgoPseudocode
import feature.search.ui.visulizer.contract.AlgoState
import feature.search.ui.visulizer.contract.AlgoStateController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


internal class AlgoControllerImpl<T : Comparable<T>>(list: List<T>, private val target: T) :
    AlgoStateController<T> {
    private val builder = LinearSearchBaseIterator(list, target)
    private val iterator = builder.result.iterator()
    override val pseudocode = builder.pseudocode
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
            currentIndex = -1,
            currentElement = null
        )


}

private class LinearSearchBaseIterator<T : Comparable<T>>(
    private val list: List<T>,
    private val target: T,
) {
    private var length = list.size
    private var index = 0 // Before search starts
    private var searchEnded = false
    private var isFound: Boolean? = null
    private var current: T? = null
    val pseudocode = AlgoPseudocode.codes

    val result = sequence {
        yield(newState())// Search not started
        updateVariablesState(length=length)
        for (i in list.indices) {
            index = i
            updatePseudocode(5)
            updateVariablesState(length = length, target = target, index = index)
            yield(newState())
            current = list[i]
            isFound = current == target
            yield(newState())
            updateVariablesState(length = length, target = target, index = index, current = current)
            if (isFound as Boolean) {
                searchEnded = true
                yield(newState()) // Target found
                updatePseudocode(10)
                break // Exit after finding the match
            }
            yield(newState())
            updateVariablesState(length = length, target = target)
            updatePseudocode(12)
            yield(newState())

        }

        if (!searchEnded) { // If the search ends without finding the target
            searchEnded = true
            updateVariablesState()
            yield(newState())
            updatePseudocode(14)
        }
    }

    private fun newState(): AlgoState<T> {
        return AlgoState(
            target = target,
            currentIndex = index,
            currentElement = current
        )
    }

    private fun updateVariablesState(
        length: Int? = null,
        target: T? = null,
        index: Int? = null,
        current: T? = null
    ) {
        AlgoPseudocode.updateStates(
            length = length,
            target = target,
            index = index,
            current = current
        )
    }

    private fun updatePseudocode(lineNo: Int) {
        AlgoPseudocode.highLightPseudocode(lineNo)

    }


}

