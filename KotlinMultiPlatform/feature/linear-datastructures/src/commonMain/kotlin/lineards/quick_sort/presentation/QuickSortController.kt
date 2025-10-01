package lineards.quick_sort.presentation

import core.ui.core.array.VisualArrayFactory
import core.ui.core.array.controller.VisualArrayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lineards._core.SortRouteControllerBase
import lineards.quick_sort.domain.QuickSortPseudocodeGenerator
import lineards.quick_sort.domain.QuickSortSimulator
import lineards.quick_sort.domain.QuickSortState

internal class QuickSortViewModel : SortRouteControllerBase() {
    // private late init var arraySimulator: ArraySimulator
    private lateinit var iterator: QuickSortSimulator
    companion object {
        private const val POINTER_I = "i"
        private const val POINTER_J = "j"
        //    private const val POINTER_PIVOT = "p" High is the pivot
        private const val POINTER_LOW = "low"
        private const val POINTER_HIGH = "High"
    }

    private val pointers = listOf(POINTER_I, POINTER_J, POINTER_LOW, POINTER_HIGH)
    override fun arrayControllerFactory(): VisualArrayController {
        // arraySimulator = ArraySimulator.create(list.value)
        //  arrayController.value = arraySimulator.arrayController
        iterator = QuickSortSimulator.create(list.value)
        return VisualArrayFactory.createController(
            itemLabels = list.value.map { it.toString() },
            pointersLabel = pointers
        )
    }

    override fun onNext() {
        val state = iterator.next()
        _code.update { QuickSortPseudocodeGenerator.generate(state) }
            if (state is QuickSortState.State) {
                movePointerI(state.i)
                movePointerJ(state.j)
                onPivotChange(state.pivot)
                onSubArrayChange(state.low, state.high)
                state.swapped?.let { (i, j) ->
                    swap(i, j)
                }
            }
            if (state is QuickSortState.Finish)
                clearPointer()


    }

     private fun movePointerI(index: Int?) {
        index?.let {
            arrayController.value?.movePointer(POINTER_I, index)
        }
    }

     private fun movePointerJ(index: Int?) {
        index?.let {
            arrayController.value?.movePointer(POINTER_J, index)
        }
    }

     private fun clearPointer() {
         arrayController.value?.removePointers(pointers)
    }

     private fun onPivotChange(index: Int) {
        //   arrayController.movePointer(POINTER_PIVOT, index)
        //high is pivot
    }

     private fun onSubArrayChange(low: Int, high: Int) {
         arrayController.value?.let {
             it.movePointer(POINTER_LOW, low)
             it.movePointer(POINTER_HIGH, high)
         }
        //InActive Part
//        for (i in 0..<low){
//            arrayController.changeElementColor(i, Color.Black)
//        }
//        for (i in high+1..list.lastIndex){
//            arrayController.changeElementColor(i, Color.Black)
//        }
//
//        for ( i in low..high){
//            arrayController.changeElementColor(i, Color.White)
//        }

    }

    fun swap(i: Int, j: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            arrayController.value?.swap(i = i, j = j, delay = 100)
        }
    }

}