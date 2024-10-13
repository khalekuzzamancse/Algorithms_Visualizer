package bubblesort.infrastructure

import bubblesort.domain.model.CodeStateModel
import bubblesort.domain.model.SimulationState
import bubblesort.domain.service.PseudocodeGenerator

internal class Iterator<T : Comparable<T>>(array: List<T>) {
    private var i = 0
    private var j = 0
    private val length = array.size
    private val lastIndex = length - 1
    private val sortedList = array.toList().toMutableList()//copy-list
    private var model = CodeStateModel(len = "$length", sortedList = "$sortedList")
    private fun CodeStateModel.toCode() = PseudocodeGenerator.generate(this)
    private val outOfScope = null

    fun start() = sequence {
        i = 0
        model = model.copy(i = "$i", j = outOfScope, swap = outOfScope, shouldSwap = outOfScope)
        yield(SimulationState.PointerIChanged(index = i, model.toCode()))

        while (i < lastIndex) { //for ( i =0 ;i <length ; i++)
            yield(SimulationState.PointerIChanged(index = i, model.toCode()))
            j = 0
//            while (j < length - i - 1) { // for ( j =0 ;j <length ; i++)
            while (j < lastIndex) { // for ( j =0 ;j <length ; i++)
                val shouldSwap = sortedList[j] > sortedList[j + 1]
                val valueAtJ = sortedList[j]
                val valueAtJPlus = sortedList[j + 1]

                model = model.copy(i = "$i", j = "$j", shouldSwap = "$shouldSwap", valueAtJ = "$valueAtJ", valueAtJPlus1 = "$valueAtJPlus")
                yield(SimulationState.PointerJChanged(index = j, model.toCode()))

                if (shouldSwap) {
                    swap()
                    model = model.copy(
                        i = "$i",
                        j = "$j",
                        shouldSwap = "true",
                        swap = "swapped($valueAtJ, $valueAtJPlus)",
                        sortedList = "$sortedList"
                    )
                    yield(SimulationState.Swap(i = j, j = j + 1, model.toCode()))
                }
                model = model.copy(shouldSwap = outOfScope, swap = outOfScope, valueAtJPlus1 = outOfScope, valueAtJ = outOfScope)
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

