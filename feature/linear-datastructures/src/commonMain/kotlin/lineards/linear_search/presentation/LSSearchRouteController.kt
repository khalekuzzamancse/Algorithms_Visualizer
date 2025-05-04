package lineards.linear_search.presentation

import core_ui.ArrayColor
import core_ui.core.array.VisualArrayFactory
import core_ui.core.array.controller.VisualArrayController
import kotlinx.coroutines.flow.update
import lineards._core.SearchRouteControllerBase
import lineards.linear_search.DiContainer
import lineards.linear_search.domain.model.DataModel
import lineards.linear_search.domain.model.SimulationState
import lineards.linear_search.domain.service.Simulator

internal class LSSearchRouteController : SearchRouteControllerBase() {
    private lateinit var _simulator: Simulator<Int>

    companion object {
        private const val POINTER_I = "i"
    }

    private val pointers = listOf(POINTER_I)


    override fun onNext() {
        arrayController.value?.let { arrayController ->
            if (_simulator.isFinished()) {
                _onFinished()
                return
            }
            val state = _simulator.next()
            _code.update { state.code }

            when (state) {
                is SimulationState.PointerI -> {
                    arrayController.movePointer(label = POINTER_I, index = state.index)
                }

                is SimulationState.FoundAt -> {
                    //Target is to found the index so make sense to highlight the cell instead of element
                    arrayController.changeCellColor(
                        state.index,
                        color = ArrayColor.FOUND_ELEMENT_COLOR
                    )
                }

                is SimulationState.Finished -> _onFinished()
                else -> {

                }
            }
        }


    }

    override fun _createController(): VisualArrayController {
        _simulator = DiContainer.createSimulator(DataModel(array = list.value, target))
        return VisualArrayFactory.createController(
            itemLabels = list.value.map { it.toString() },
            pointersLabel = pointers
        )
    }

    override fun onReset() {
        super.onReset()
        _simulator = DiContainer.createSimulator(DataModel(array = list.value, target))
    }

    override fun _onFinished() {
        super._onFinished()
        arrayController.value?.removePointers(pointers)
    }


}