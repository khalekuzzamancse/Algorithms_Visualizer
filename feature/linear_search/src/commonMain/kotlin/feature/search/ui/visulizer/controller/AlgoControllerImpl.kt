package feature.search.ui.visulizer.controller

import feature.search.ui.visulizer.contract.AlgoPseudocode
import feature.search.ui.visulizer.contract.AlgoStateController
import feature.search.ui.visulizer.contract.SimulationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


internal class AlgoControllerImpl<T : Comparable<T>>(list: List<T>, private val target: T) :
    AlgoStateController<T> {
    private val builder = LinearSearchBaseIterator(list, target)
    private val iterator = builder.result.iterator()
    override val pseudocode = builder.pseudocode
    private val _state = MutableStateFlow<SimulationState>(initializeState())
    override val algoState: StateFlow<SimulationState> = _state.asStateFlow()


    override fun next() {
        if (iterator.hasNext()) {
            val res = iterator.next()
            _state.update { res }
        }

    }

    override fun hasNext() = iterator.hasNext()
    private fun initializeState(): SimulationState.AlgoState<T> =
        SimulationState.AlgoState(
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
    val algoPseudocode=AlgoPseudocode()
    val pseudocode =algoPseudocode.codes

    val result = sequence {
        yield(newState())// Search not started
        updateVariablesState(length = length)
        for (i in list.indices) {
            index = i
            updatePseudocode(5)
            updateVariablesState(length = length, target = target, index = index)
            yield(newState())
            current = list[i]
            isFound = current == target

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

        }

        if (!searchEnded) { // If the search ends without finding the target
            searchEnded = true
            index = -1
            updateVariablesState()
            updatePseudocode(14)
        }
        yield(createEndState())
    }

    private fun createEndState(): SimulationState.Finished {
        val isFound = index != -1
        return SimulationState.Finished(
            foundedIndex = index,
            comparisons = if (isFound) index + 1 else list.size
        )
    }

    private fun newState(): SimulationState.AlgoState<T> {
        return SimulationState.AlgoState(
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
        algoPseudocode.updateStates(
            length = length,
            target = target,
            index = index,
            current = current
        )
    }

    private fun updatePseudocode(lineNo: Int) {
        algoPseudocode.highLightPseudocode(lineNo)

    }


}

