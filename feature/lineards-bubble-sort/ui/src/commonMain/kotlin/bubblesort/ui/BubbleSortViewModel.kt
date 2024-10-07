@file:Suppress("functionName")

package bubblesort.ui

import bubblesort.di.DiContainer
import bubblesort.domain.model.DataModel
import bubblesort.domain.model.SimulationState
import bubblesort.domain.service.Simulator
import bubblesort.presentationlogic.PresentationFactory.createAutoPlayer
import core.commonui.array.VisualArrayFactory
import core.commonui.array.controller.VisualArrayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


internal class BubbleSortViewModel(
    private val color: StatusColor
) {
    private val _inputMode = MutableStateFlow(true)
    private val _array = MutableStateFlow(listOf(10, 5, 4, 13, 8))
    private val list = _array.asStateFlow()
    private lateinit var simulator: Simulator<Int>
    private val scope = CoroutineScope(Dispatchers.Default)

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
                is SimulationState.PointerIChanged -> {
                    val index=state.index
                    arrayController.movePointer(label = "i", index =index)
                    arrayController.changeElementColor(index,color.iPointerLocation)

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
                    arrayController.clearPointers(listOf("j","j+1"))
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
            pointersLabel = listOf("i", "j", "j+1")
        )
        simulator = DiContainer.createSimulator(DataModel(array = list.value))
    }


}