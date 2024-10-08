package insertionsort.infrastructure

import insertionsort.domain.model.SimulationState


internal class Iterator<T : Comparable<T>>(array: List<T>) {
    private var i = 1 // Start at 1 since the element at index 0 is considered sorted
    private var j = 0
    private val length = array.size
    private val sortedList = array.toList().toMutableList()

    fun start() = sequence {
        i = 1

        yield(SimulationState.PointerIChanged(index = i, sortedUpTo =i-1))


        while (i < length) {
            yield(SimulationState.PointerIChanged(index = i, sortedUpTo = i-1))
            j = i


            while (j > 0) {
                yield(SimulationState.BackwardPointerChanged(j = j, `j-1` = j-1))

                val shouldSwap=sortedList[j] < sortedList[j - 1]
                if (shouldSwap) {
                    swap(j, j - 1)
                    yield(SimulationState.Swap(i = j, j = j - 1))
                    j--
                } else {
                    break
                }
            }
            yield(SimulationState.ClearBackwardPointer)//j is will be destroyed soon
            i++


        }
    }

    // Helper methods -----------------------
    private fun swap(index1: Int, index2: Int) {
        val temp = sortedList[index1]
        sortedList[index1] = sortedList[index2]
        sortedList[index2] = temp
    }
}
