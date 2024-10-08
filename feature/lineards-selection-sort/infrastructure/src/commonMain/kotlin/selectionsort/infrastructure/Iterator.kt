package selectionsort.infrastructure

import selectionsort.domain.model.SimulationState


internal class Iterator<T : Comparable<T>>(array: List<T>) {
    private var i = 0
    private var j = 0
    private val length = array.size
    private val lastIndex = length - 1
    private val sortedList = array.toList().toMutableList()//copy-list
    fun start() = sequence {
        i = 0
        yield(SimulationState.PointerIChanged(index = i))

        while (i < lastIndex) { //for ( i =0 ;i <length ; i++)

            yield(SimulationState.PointerIChanged(index = i))

            val minIndex = findMinimumIndex(i)

            yield(SimulationState.PointerMinIndexChanged(index = minIndex))

            if (isMinFound(i, minIndex)) {
                swap(i, minIndex)
                yield(SimulationState.Swap(i = i, j = minIndex))
            }

            i++
            yield(SimulationState.ClearMinIndex)
        }
    }


    //TODO:Helper methods-------------
    //TODO:Helper methods-------------
    //TODO:Helper methods-------------
    private fun swap(index1: Int, index2: Int) {
        val temp = sortedList[index1]
        sortedList[index1] = sortedList[index2]
        sortedList[index2] = temp
    }

    /**if the min index,is not the index from where we started to find the min: isMinFound=(minIndex!=i)*/
    private fun isMinFound(i: Int, minIndex: Int): Boolean = (minIndex != i)
    private fun findMinimumIndex(i: Int): Int {
        var minIndex = i
        j = i + 1
        while (j < length) {
            if (sortedList[j] < sortedList[minIndex]) {
                minIndex = j
            }
            j++
        }
        return minIndex
    }


}
