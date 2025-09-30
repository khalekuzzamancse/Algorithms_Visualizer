package lineards.insertion_sort.inf

import lineards.insertion_sort.domain.model.CodeStateModel
import lineards.insertion_sort.domain.model.SimulationState
import lineards.insertion_sort.domain.service.PseudocodeGenerator


internal class Iterator<T : Comparable<T>>(array: List<T>) {
    private var i = 1 // Start at 1 since the element at index 0 is considered sorted
    private var j = 0
    private val length = array.size
    private val sortedList = array.toList().toMutableList()
    private var model = CodeStateModel(len = "$length", list = "$sortedList")
    private fun CodeStateModel.toCode() = PseudocodeGenerator.generate(this)

    fun start() = sequence {
        i = 1
        model = model.copy(i = "1", list = "$sortedList")
        yield(SimulationState.PointerIChanged(index = i, sortedUpTo =i-1, code = model.toCode()))


        while (i < length) {
            model = model.copy(i = "$i")
            yield(SimulationState.PointerIChanged(index = i, sortedUpTo = i-1, code = model.toCode()))

            j = i


            while (j > 0) {
                model = model.copy(j= "$j")
                yield(SimulationState.BackwardPointerChanged(j = j, `j-1` = j-1, code = model.toCode()))

                val shouldSwap=sortedList[j] < sortedList[j - 1]

                model = model.copy(shouldSwap = "$shouldSwap", valueAtJ = "${sortedList[j]}", valueAtJmiuns1 ="${sortedList[j - 1]}" )
                if (shouldSwap) {
                    swap(j, j - 1)
                    model = model.copy(swap = "swapped(${sortedList[j]},${sortedList[j - 1]})" )
                    yield(SimulationState.Swap(i = j, j = j - 1, code = model.toCode()))
                    j--
                } else {
                    break
                }
            }
            model=model.jDead().swapDead().shouldSwapDead()
            yield(SimulationState.ClearBackwardPointer(code = model.toCode()))//j is will be destroyed soon
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
