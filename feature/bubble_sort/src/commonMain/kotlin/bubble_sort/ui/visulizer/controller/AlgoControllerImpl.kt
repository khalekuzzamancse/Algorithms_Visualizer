package bubble_sort.ui.visulizer.controller

import bubble_sort.ui.visulizer.contract.AlgoState
import bubble_sort.ui.visulizer.contract.AlgoStateController
import bubble_sort.ui.visulizer.contract.Pseudocode
import bubble_sort.ui.visulizer.contract.SwappedElement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class AlgoControllerImpl<T : Comparable<T>>(list: List<T>) :
    AlgoStateController<T> {
    private val builder = BubbleSortSequence(list)
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
            i = null,
            j = null,
            swapAblePair = null,
            shouldSwap = null,
            ended = false
        )


}

internal class BubbleSortSequence<T : Comparable<T>>(
    array: List<T>
) {
    private val n = array.size
    private var i = 0
    private var j = 0
    private val list = array.toMutableList()
    private var swappedElement:SwappedElement<T> ?=null
    val result = sequence {
        i = 0
        while (i < n - 1) {
            yield(newState()) //Line=2: for each i ,send the result
            j = 0
            while (j < n - i - 1) {
                if (list[j] > list[j + 1]) {
                    swappedElement= SwappedElement(
                        j = j,
                        jPlus1 = j+1,
                        jValue = list[j],
                        jPlus1Value = list[j+1]
                    )//before swap notify about the swap about element
                    swap()

                    yield(newState()) //notify swapped
                    swappedElement=null//clear old swap element
                }
                j++
                yield(newState()) //notify j changed
            }
            i++
            swappedElement=null //clear old swap element
            //no need to notify here because the next Line=2 ,will be notify
        }
    }

    private fun swap() {
        val j=this.j
        val jNeighbour=j+1
        val temp = list[j]
        list[j] = list[jNeighbour]
        list[jNeighbour] = temp
    }

    private fun newState(): AlgoState<T> {
        return AlgoState(
            i = i,
            j = j,
            shouldSwap=false,
            swapAblePair = swappedElement,
            ended = false,
        )
    }
}
