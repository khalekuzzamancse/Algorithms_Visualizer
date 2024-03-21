package bubble_sort.infrastructure

import bubble_sort.domain.BaseIterator
import bubble_sort.domain.SwappedPair

internal class BubbleSortIterator<T : Comparable<T>>(array: List<T>) : BaseIterator<T>(array) {
    override val result = sequence {
        i = 0
        updateVariablesState(len = length)
        yield(newState())
        while (i < length - 1) { //for ( i =0 ;i <length ; i++)
            updateVariablesState(len = length, i = i)
            yield(newState())
            j = 0
            while (j < length - i - 1) { ////for ( j =0 ;j <length ; i++)
                updateVariablesState(
                    len = length,
                    i = i,
                    j = j,
                )
                yield(newState())
                if (sortedList[j] > sortedList[j + 1]) {
                    swappablePair = SwappedPair(
                        j = j,
                        jPlus1 = j + 1,
                        jValue = sortedList[j],
                        jPlus1Value = sortedList[j + 1]
                    )//before swap notify about the swap about element
                    swap()
                    yield(newState()) //notify swapped
                    swappablePair = null//clear old swap element
                    updateVariablesState(
                        len = length,
                        i = i,
                        j = j,
                        jValue = sortedList[j],
                        jPlus1Value = sortedList[j + 1]
                    )
                }
                j++
                yield(newState()) //notify j changed
            }
            i++
            swappablePair = null //clear old swap element
            //no need to notify here because the next Line=2 ,will be notify
        }
        yield(createEndState()) //notify ended
    }

    private fun swap() {
        val j = this.j
        val jNeighbour = j + 1
        val temp = sortedList[j]
        sortedList[j] = sortedList[jNeighbour]
        sortedList[jNeighbour] = temp
    }


}
