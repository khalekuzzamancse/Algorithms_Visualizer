package selection_sort.infrastructure

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import selection_sort.DebuggablePseudocodeBuilder
import selection_sort.PseudoCodeVariablesValue
import selection_sort.domain.AlgoSimulator
import selection_sort.domain.AlgoState
import selection_sort.domain.SwappedElement

internal class AlgoSimulatorImpl<T : Comparable<T>>(list: List<T>) : AlgoSimulator<T> {
    private val builder = SelectionSortSequence(list)
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

    private val pseudocodeBuilder = DebuggablePseudocodeBuilder()
    private val _pseudocode =
        MutableStateFlow(pseudocodeBuilder.build())//initial code with no debug text
    val pseudocode = _pseudocode.asStateFlow()


    val result = sequence {
        i = 0
        //`i` is initialized , update the value  in the pseudocode
        updatePseudocode()

        while (i < n - 1) {


            yield(newState())
            val minIndex = findMinimumIndex(i)
            //`i` is increased update the value  in the pseudocode

            updatePseudocode(
                minIndex = minIndex,
            )
            //------------------------------------------------
            yield(newState(minIndex)) // notify about min  index


            if (minIndex != i) {// if the min index,is not the index from where we started to find the min: isMinFound=(minIndex!=i)

                swap(i, minIndex)
                //clear the min index and swapping info


                swappablePair = SwappedElement(i, minIndex, list[i], list[minIndex])

                yield(newState()) //notify for swap and make minIndex=null


                //clear the swappable pair,because multiple time calling will make unwanted swap
                swappablePair = null
            }

            i++
            updatePseudocode()


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


    //TODO : Helper function section

    /**
     * - if no value passed,then i ,len wil be automatically updated
     * - if [minIndex] passed then along with `i` other will be updated
     */
    fun updatePseudocode(
        minIndex: Int? = null,
    ) {
        _pseudocode.update {
            val isMinFound = if (minIndex == null) null else if (minIndex != i) true else false
            pseudocodeBuilder.build(
                PseudoCodeVariablesValue(
                    i = i.toString(),
                    len = list.size.toString(),
                    minIndex = minIndex?.toString(),
                    min =if (isMinFound!=null &&isMinFound&&minIndex!=null) "${list[minIndex]}" else null,
                    current =if (minIndex!=null) "${list[i]}" else null ,
                    isMinFound = isMinFound?.toString(),
                )
            )
        }
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

