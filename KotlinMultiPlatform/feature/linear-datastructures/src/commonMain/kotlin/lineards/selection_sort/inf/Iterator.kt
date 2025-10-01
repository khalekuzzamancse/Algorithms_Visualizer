package lineards.selection_sort.inf

import lineards.selection_sort.domain.model.CodeStateModel
import lineards.selection_sort.domain.model.SimulationState
import lineards.selection_sort.domain.service.PseudocodeGenerator


internal class Iterator<T : Comparable<T>>(array: List<T>) {
    private var i = 0
    private var j = 0
    private val length = array.size
    private val lastIndex = length - 1
    private val sortedList = array.toList().toMutableList()//copy-list
    private var model = CodeStateModel(len = "$length", list = "$sortedList")
    private fun CodeStateModel.toCode() = PseudocodeGenerator.generate(this)

    fun start() = sequence {
        i = 0
        model = model.copy(i = "0", list = "$sortedList")
        yield(SimulationState.PointerIChanged(index = i, model.toCode()))

        while (i < lastIndex) { //for ( i =0 ;i <length ; i++)
            model = model.copy(i = "$i")
            yield(SimulationState.PointerIChanged(index = i,model.toCode()))

            val minIndex = findMinimumIndex(i)
            val isFound=isMinFound(i,minIndex)

            model = model.copy(minIndex = if (isFound)"$minIndex" else "null", isMinFound = "$isFound",list = "$sortedList")

            yield(SimulationState.PointerMinIndexChanged(index = minIndex, code = model.toCode()))

            if (isMinFound(i, minIndex)) {
                swap(i, minIndex)
                model = model.copy(swap = "swapped(${sortedList[i]},${sortedList[minIndex]})",list = "$sortedList")
                yield(SimulationState.Swap(i = i, j = minIndex, code = model.toCode()))
            }
            i++
            model=model.minIndexDead().isMinFoundDead().swapDead()
            yield(SimulationState.ClearMinIndex(model.toCode()))
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
