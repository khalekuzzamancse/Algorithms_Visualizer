package insertion_sort.infrastructure

import insertion_sort.domain.AlgoState
import insertion_sort.domain.AlgoStateController
import insertion_sort.domain.LineForPseudocode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class AlgoControllerImpl<T : Comparable<T>>(list: List<T>) :
    AlgoStateController<T> {
    private val builder = InsertionSortSequence(list)
    private val iterator = builder.result.iterator()
    override val pseudocode = MutableStateFlow(emptyList<LineForPseudocode>())
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
            keyFinalPosition = null,
            shiftedIndex = null
        )


}

/**
 * See the module docs to understand how the algorithms works
 */
internal class InsertionSortSequence<T : Comparable<T>>(
    array: List<T>
) {
    private val n = array.size
    private var i = 1
    private var shiftThisIndexBy1: Int? = null
    private val list = array.toMutableList()
    private var keyFinalPosition: Int? = null
    private var _key: T? = null
    private var _j: Int? = null
    val result = sequence {
        while (i < n) {
            yield(newState()) // Notify i changed
            val key = list[i] //key=current element
            _key = list[i]
            var j = i - 1
            while (needsShifting(j, key)) {
                _j = j
                yield(newState()) // Notify for new j

                shiftLeftElementToRight(j) //list[j + 1] = list[j]
                shiftThisIndexBy1 = j

                yield(newState()) // Notify an element(j] is shifted to right
                clearOldShiftingInfo() //shiftThisIndexBy1 = null
                j -= 1

            }
            list[j + 1] = key
            keyFinalPosition = j + 1
            yield(newState()) // Notify key is placed at right points
            keyFinalPosition = null //clear old key final position to avoid conflict
            _key = null //clear the old key
            _j = null  //clear the old j
            i++
        }
        yield(endedState())
    }


    private fun clearOldShiftingInfo(){
        shiftThisIndexBy1 = null //clear old shifting info
    }
    private fun shiftLeftElementToRight(j:Int){
        list[j + 1] = list[j]
    }
    private fun needsShifting(j:Int,key:T)= j >= 0 && list[j] > key


    private fun newState(): AlgoState<T> {
        return AlgoState(
            i = i,
            keyFinalPosition = keyFinalPosition,
            shiftedIndex = shiftThisIndexBy1,
            key = _key,
            j = _j
        )
    }

    private fun endedState(): AlgoState<T> {
        return AlgoState(
            i = null,
            keyFinalPosition = keyFinalPosition,
            shiftedIndex = null,
            j = null
        )
    }
}

