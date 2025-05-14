@file:Suppress("functionName")

package lineards.insertion_sort.presentation

import core.ui.ArrayColor
import core.ui.core.array.VisualArrayFactory
import core.ui.core.array.controller.VisualArrayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lineards.DiContainer
import lineards._core.SortRouteControllerBase
import lineards.insertion_sort.domain.model.DataModel
import lineards.insertion_sort.domain.model.SimulationState
import lineards.insertion_sort.domain.service.Simulator


internal class SimulationViewModel: SortRouteControllerBase() {
    private lateinit var simulator: Simulator<Int>
    private val scope = CoroutineScope(Dispatchers.Default)

    companion object {
        private const val POINTER_I = "i"
        private const val POINTER_J = "j"
        private const val POINTER_J_MINUS_1 = "j-1"
    }

    private val pointers = listOf(POINTER_I, POINTER_J, POINTER_J_MINUS_1)



    override fun onNext() {
        arrayController.value?.let { arrayController ->
            val state = simulator.next()
            _code.update { state.code }
            when (state) {
                is SimulationState.BackwardPointerChanged -> {
                    val (j, jPrev) = state
                    arrayController.movePointer(label = POINTER_J, index = j)
                    arrayController.movePointer(label = POINTER_J_MINUS_1, index = jPrev)
                }

                is SimulationState.ClearBackwardPointer -> {
                    arrayController.hidePointer(POINTER_J)
                    arrayController.hidePointer(POINTER_J_MINUS_1)
                }

                is SimulationState.PointerIChanged -> {
                    val i = state.index
                    val sortedUpTo=state.sortedUpTo
                    arrayController.movePointer(label = POINTER_I, index = i)
                    arrayController.changeElementColor(i, ArrayColor.CURRENT_ELEMENT)
                    arrayController.changeCellColorUpTo(sortedUpTo, ArrayColor.VISITED_ELEMENT_COLOR)
                }


                is SimulationState.Swap -> {
                    scope.launch {
                        arrayController.swap(i = state.i, j = state.j, delay = 100)
                    }

                }


                is SimulationState.Finished -> {
                    arrayController.removePointers(listOf(POINTER_I,POINTER_J, POINTER_J_MINUS_1))

                }

                else -> {

                }
            }
        }


    }


     override fun _createController(): VisualArrayController {
        simulator = DiContainer.createInsertionSortSimulator(DataModel(array = list.value))
         return VisualArrayFactory.createController(
             itemLabels = list.value.map { it.toString() },
             pointersLabel = pointers
         )
    }


}