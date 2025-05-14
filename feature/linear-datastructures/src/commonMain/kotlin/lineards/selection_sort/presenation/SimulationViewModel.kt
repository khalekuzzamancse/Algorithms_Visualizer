@file:Suppress("functionName")

package lineards.selection_sort.presenation

import core.ui.ArrayColor
import core.ui.core.array.VisualArrayFactory
import core.ui.core.array.controller.VisualArrayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lineards.DiContainer
import lineards._core.SortRouteControllerBase
import lineards.selection_sort.domain.model.DataModel
import lineards.selection_sort.domain.model.SimulationState
import lineards.selection_sort.domain.service.Simulator


internal class SimulationViewModel : SortRouteControllerBase() {
    private lateinit var simulator: Simulator<Int>
    private val scope = CoroutineScope(Dispatchers.Default)


    companion object {
        private const val POINTER_I = "i"
        private const val POINTER_MIN_INDEX = "min"
    }

    val pointers = listOf(POINTER_I, POINTER_MIN_INDEX)


    override fun onNext() {
        arrayController.value?.let { arrayController ->
            val state = simulator.next()
            _code.update { state.code }
            when (state) {
                is SimulationState.PointerMinIndexChanged -> {
                    val index = state.index
                    arrayController.movePointer(label = POINTER_MIN_INDEX, index = index)
                }

                is SimulationState.ClearMinIndex -> {
                    arrayController.hidePointer(POINTER_MIN_INDEX)
                }

                is SimulationState.PointerIChanged -> {
                    val index = state.index
                    arrayController.movePointer(label = POINTER_I, index = index)
                    arrayController.changeElementColor(index, ArrayColor.VISITED_ELEMENT_COLOR)
                }


                is SimulationState.Swap -> {
                    scope.launch {
                        arrayController.swap(i = state.i, j = state.j, delay = 100)
                    }

                }

                is SimulationState.Finished -> arrayController.removePointers(
                    listOf(
                        POINTER_MIN_INDEX
                    )
                )

                else -> {

                }
            }
        }


    }

    override fun _createController(): VisualArrayController {
        simulator = DiContainer.createSelectionSortSimulator(DataModel(array = list.value))
        return VisualArrayFactory.createController(
            itemLabels = list.value.map { it.toString() },
            pointersLabel = pointers
        )

    }


}