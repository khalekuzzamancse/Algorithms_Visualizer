@file:Suppress("functionName")
package lineards.binary_search.presentation
import core.ui.ArrayColor
import core.ui.core.array.VisualArrayFactory
import core.ui.core.array.controller.VisualArrayController
import kotlinx.coroutines.flow.update
import lineards.binary_search.DiContainer
import lineards.binary_search.domain.model.DataModel
import lineards.binary_search.domain.model.SimulationState
import lineards.binary_search.domain.service.Simulator
import lineards._core.SearchRouteControllerBase

internal class BSViewModel: SearchRouteControllerBase() {
    private lateinit var simulator: Simulator<Int>

    companion object {
        private const val POINTER_LOW = "low"
        private const val POINTER_HIGH = "high"
        private const val POINTER_MID = "mid"
    }

    private val pointers = listOf(POINTER_LOW, POINTER_HIGH, POINTER_MID)



    override fun onNext() {
        if (simulator.isFinished()) {
            _onFinished()
            return
        }
        val state = simulator.next()
        _code.update { state.code }


        arrayController.value?.let { arrayController ->
            val visitedColor= ArrayColor.VISITED_ELEMENT_COLOR
            when (state) {
                is SimulationState.PointerLow -> {
                    arrayController.movePointer(label = POINTER_LOW, index = state.index)
                    arrayController.changeElementColor(index = state.index, color = visitedColor)
                }
                is SimulationState.PointerHigh -> {
                    arrayController.movePointer(label = POINTER_HIGH, index = state.index)
                    arrayController.changeElementColor(index = state.index, color = visitedColor)
                }
                is SimulationState.PointerMid -> {
                    arrayController.movePointer(label = POINTER_MID, index = state.index)
                    arrayController.changeElementColor(index = state.index, color = visitedColor)
                }
                is SimulationState.ClearMid->{
                    arrayController.hidePointer(POINTER_MID)
                }

                is SimulationState.FoundAt -> {
                    arrayController.changeElementColor(index = state.index, color = ArrayColor.FOUND_ELEMENT_COLOR)
                }

                is SimulationState.Finished -> {
                    arrayController.removePointers(pointers)
                    _onFinished()
                }

                else -> {}
            }
        }


    }

    override fun _onFinished() {
        super._onFinished()
        arrayController.value?.removePointers(pointers)
    }


     override fun _createController(): VisualArrayController {
        simulator = DiContainer.createSimulator(DataModel(array = list.value, target))
         return  VisualArrayFactory.createController(
             itemLabels = list.value.map { it.toString() },
             pointersLabel = pointers
         )
    }


}
