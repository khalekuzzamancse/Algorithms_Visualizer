package lineards.quick_sort.presentation

import core.ui.core.array.VisualArrayFactory
import core.ui.core.array.controller.VisualArrayController
import kotlinx.coroutines.flow.update
import lineards._core.SortRouteControllerBase
import lineards.selection_sort.domain.service.Simulator
import quick_sort.data.QuickSortIterator
import quick_sort.data.QuickSortState

internal class QuickSortController: SortRouteControllerBase() {
    private lateinit var simulator: Simulator<Int>
    private lateinit var arraySimulator: ArraySimulator
    private lateinit var iterator: QuickSortIterator

    init {
        autoPlayer.onNextCallback = ::onNext
    }
    companion object {
        private const val POINTER_I = "i"
        private const val POINTER_J = "j"
        //    private const val POINTER_PIVOT = "p" High is the pivot
        private const val POINTER_LOW = "low"
        private const val POINTER_HIGH = "High"
    }

    private val pointers = listOf(POINTER_I, POINTER_J, POINTER_LOW, POINTER_HIGH)

    override fun arrayControllerFactory(): VisualArrayController {
        arraySimulator = ArraySimulator.create(list.value)
        iterator = QuickSortIterator.create(list.value)
        return VisualArrayFactory.createController(
            itemLabels = list.value.map { it.toString() },
            pointersLabel =pointers
        )
    }


    override fun onNext() {
//        arrayController.value?.let { arrayController ->
//            val state = iterator.next()
//            _code.update { state.code }
//        }
//        val state = iterator.next()
//        if (state is QuickSortState.State) {
//            arraySimulator.movePointerI(state.i)
//            arraySimulator.movePointerJ(state.j)
//            arraySimulator.onPivotChange(state.pivot)
//            arraySimulator.onSubArrayChange(state.low, state.high)
//            state.swapped?.let { (i, j) ->
//                arraySimulator.swap(i, j)
//            }
//        }
//        if(state is QuickSortState.Finish)
//            arraySimulator.clearPointer()
    }


}