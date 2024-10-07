package bubblesort.infrastructure

import bubblesort.domain.model.SimulationState


internal class BubbleSortIterator<T : Comparable<T>>(array: List<T>) {
    private var i = 0
    private var j = 0
    private val length = array.size
    private val lastIndex=length-1
    private val sortedList = array.toList().toMutableList()//copy-list
    fun start() = sequence {
        i = 0
        yield(SimulationState.PointerIChanged(index = i))

        while (i < lastIndex) { //for ( i =0 ;i <length ; i++)
            yield(SimulationState.PointerIChanged(index = i))
            j = 0
//            while (j < length - i - 1) { // for ( j =0 ;j <length ; i++)
            while (j < lastIndex) { // for ( j =0 ;j <length ; i++)
                yield(SimulationState.PointerJChanged(index = j))

                val shouldSwap = sortedList[j] > sortedList[j + 1]
                if (shouldSwap) {
                    swap()
                    yield(SimulationState.Swap(i = j, j = j+1))
                }
                j++
            }
            i++
            //no need to notify here because the next Line=2 ,will be notify
        }
    }

    private fun swap() {
        val j = this.j
        val jNeighbour = j + 1
        val temp = sortedList[j]
        sortedList[j] = sortedList[jNeighbour]
        sortedList[jNeighbour] = temp
    }


}
