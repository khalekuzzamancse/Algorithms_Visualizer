@file:Suppress("functionName")

package binarysearch.ui

import binarysearch.di.DiContainer
import binarysearch.domain.model.DataModel
import binarysearch.domain.model.SimulationState
import binarysearch.domain.service.Simulator
import binarysearch.presentationlogic.PresentationFactory.createAutoPlayer
import core.commonui.array.VisualArrayFactory
import core.commonui.array.controller.VisualArrayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


internal class SimulationViewModel(
    private val color: StatusColor
) {
    private val _inputMode = MutableStateFlow(true)
    private val _array = MutableStateFlow(listOf(10, 5, 4, 13, 8))
    private val list = _array.asStateFlow()
    private lateinit var simulator: Simulator<Int>
    private val scope = CoroutineScope(Dispatchers.Default)
    private val _code = MutableStateFlow<String?>(null)
    val code = _code.asStateFlow()


    companion object {
        private const val POINTER_LOW = "low"
        private const val POINTER_HIGH = "high"
        private const val POINTER_MID = "mid"
    }

    private val pointers = listOf(POINTER_LOW, POINTER_HIGH, POINTER_MID)

    val inputMode = _inputMode.asStateFlow()
    val arrayController = MutableStateFlow<VisualArrayController?>(null)
    private var target: Int = 0
    //Will be used for reset that is why observable

    val autoPlayer = createAutoPlayer()

    init {
        autoPlayer.onNextCallback = ::onNext
    }

    fun onInputComplete(inputData: List<Int>, target: Int) {
        this.target = target
        _array.update { inputData }
        _inputMode.update { false }
        _createController()
    }


    fun onNext() {
        val state = simulator.next()
        _code.update { state.code }

        arrayController.value?.let { arrayController ->
            when (state) {
                is SimulationState.PointerLow -> {
                    arrayController.movePointer(label = POINTER_LOW, index = state.index)
                    arrayController.changeElementColor(index = state.index, color = color.visited)
                }
                is SimulationState.PointerHigh -> {
                    arrayController.movePointer(label = POINTER_HIGH, index = state.index)
                    arrayController.changeElementColor(index = state.index, color = color.visited)
                }
                is SimulationState.PointerMid -> {
                    arrayController.movePointer(label = POINTER_MID, index = state.index)
                    arrayController.changeElementColor(index = state.index, color = color.visited)
                }
                is SimulationState.ClearMid->{
                    arrayController.hidePointer(POINTER_MID)
                }

                is SimulationState.FoundAt -> {
                    arrayController.changeElementColor(index = state.index, color = color.foundAt)
                }

                is SimulationState.Finished -> {
                    arrayController.removePointers(pointers)
                }

                else -> {

                }
            }
        }


    }

    fun onReset() {
        autoPlayer.dismiss()
        _createController()

    }


    private fun _createController() {
        arrayController.value = VisualArrayFactory.createController(
            itemLabels = list.value.map { it.toString() },
            pointersLabel = pointers
        )
        simulator = DiContainer.createSimulator(DataModel(array = list.value, target))
    }


}