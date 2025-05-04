@file:Suppress("functionName")

package lineards.bubble_sort.presentation

import bubblesort.domain.service.Simulator
import core_ui.ArrayColor
import core_ui.core.array.VisualArrayFactory
import core_ui.core.array.controller.VisualArrayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lineards.bubble_sort.domain.model.DataModel
import lineards.bubble_sort.domain.model.SimulationState
import lineards._core.SortRouteControllerBase


internal class BubbleSortViewModel: SortRouteControllerBase() {
    private val  scope= CoroutineScope(Dispatchers.Default)
    private lateinit var simulator: Simulator<Int>


    override fun onNext() {
        val state = simulator.next()
        _code.update { state.code }
        arrayController.value?.let { arrayController ->
            when (state) {
                is SimulationState.PointerIChanged -> {
                    val index=state.index
                    arrayController.movePointer(label = "i", index =index)
                    arrayController.changeElementColor(index,ArrayColor.CURRENT_ELEMENT)

                }
                is SimulationState.PointerJChanged -> {
                    arrayController.movePointer(label = "j", index = state.index)
                    arrayController.movePointer(label = "j+1", index = state.index + 1)
                }



                is SimulationState.Swap -> {
                    scope.launch {
                        arrayController.swap(i = state.i, j = state.i + 1, delay = 100)
                    }

                }
                else -> {
                    arrayController.removePointers(listOf("j","j+1"))
                }
            }
        }


    }



     override fun _createController(): VisualArrayController {
        simulator = DiContainer.createSimulator(DataModel(array = list.value))
         return VisualArrayFactory.createController(
             itemLabels = list.value.map { it.toString() },
             pointersLabel = listOf("i", "j", "j+1")
         )
    }

}