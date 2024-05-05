package selection_sort.infrastructure

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import selection_sort.di.Factory
import selection_sort.domain.AlgoSimulator
import selection_sort.domain.AlgoState
import selection_sort.domain.SwappedElement
import selection_sort.domain.Pseudocode

internal class AlgoSimulatorImpl<T : Comparable<T>>(list: List<T>) : AlgoSimulator<T> {
    private val builder = SelectionSortSequence(list)
    private val iterator = builder.result.iterator()
    override val pseudocode = Factory.createAlgoPseudocode().codes
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
            i = null,
            minIndex = null,
            swappablePair = null
        )


}
private class SelectionSortSequence<T : Comparable<T>>(
    array: List<T>
) {
    private val n = array.size
    private var i = 0
    private var j = 0
    private val list = array.toMutableList()
    private var swappablePair: SwappedElement<T>? = null


    val result = sequence {
        i = 0
        while (i < n - 1) {
            yield(newState()) //Line=2: for each i ,send the result
            val minIndex = findMinimumIndex(i) // Direct assignment, non-nullable
            yield(newState(minIndex)) // notify about min  index
            if (minIndex != i) {// if the min index,is not the index from where we started to find the min: isMinFound=(minIndex!=i)
                swap(i, minIndex)
                swappablePair = SwappedElement(i, minIndex, list[i], list[minIndex])
                yield(newState()) //notify for swap and make minIndex=null
                //clear the swappable pair,because multiple time calling will make unwanted swap
                swappablePair=null
            }
            i++
        }
        yield(endedState())
    }

    private fun findMinimumIndex(i: Int): Int {
        var minIndex = i
        j = i + 1
        while (j < n) {
            if (list[j] < list[minIndex]) {
                minIndex = j
            }
            j++
        }
        return minIndex
    }

    private fun swap(index1: Int, index2: Int) {
        val temp = list[index1]
        list[index1] = list[index2]
        list[index2] = temp
    }

    private fun newState(minIndex: Int? = null): AlgoState<T> {
        return AlgoState(
            i = i,
            minIndex = minIndex,
            swappablePair = swappablePair
        )
    }

    private fun endedState(): AlgoState<T> {
        return AlgoState(
            i = null,
            minIndex = null,
            swappablePair = null
        )
    }
}
