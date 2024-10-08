@file:Suppress("functionName")

package selectionsort.ui

import selectionsort.di.DiContainer
import selectionsort.domain.model.DataModel
import selectionsort.domain.model.SimulationState
import selectionsort.domain.service.Simulator
import selectionsort.presentationlogic.PresentationFactory.createAutoPlayer
import core.commonui.array.VisualArrayFactory
import core.commonui.array.controller.VisualArrayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


internal class SimulationViewModel(
    private val color: StatusColor
) {
    private val _inputMode = MutableStateFlow(true)
    private val _array = MutableStateFlow(listOf(10, 5, 4, 13, 8))
    private val list = _array.asStateFlow()
    private lateinit var simulator: Simulator<Int>
    private val scope = CoroutineScope(Dispatchers.Default)

    companion object {
        private const val POINTER_I = "i"
        private const val POINTER_MIN_INDEX = "min"
    }

    val pointers = listOf(POINTER_I, POINTER_MIN_INDEX)

    val inputMode = _inputMode.asStateFlow()
    val arrayController = MutableStateFlow<VisualArrayController?>(null)
    //Will be used for reset that is why observable

    val autoPlayer = createAutoPlayer()

    init {
        autoPlayer.onNextCallback = ::onNext
    }

    fun onInputComplete(inputData: List<Int>) {
        _array.update { inputData }
        _inputMode.update { false }
        _createController()
    }


    fun onNext() {
        arrayController.value?.let { arrayController ->
            when (val state = simulator.next()) {
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
                    arrayController.changeElementColor(index, color.iPointerLocation)
                }


                is SimulationState.Swap -> {
                    scope.launch {
                        arrayController.swap(i = state.i, j = state.j, delay = 100)
                    }

                }
                is SimulationState.Finished->arrayController.removePointers(listOf(POINTER_MIN_INDEX))

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
            pointersLabel =pointers
        )
        simulator = DiContainer.createSimulator(DataModel(array = list.value))
    }


}