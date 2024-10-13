@file:Suppress("functionName")

package insertionsort.ui

import insertionsort.di.DiContainer
import insertionsort.domain.model.DataModel
import insertionsort.domain.model.SimulationState
import insertionsort.domain.service.Simulator
import insertionsort.presentationlogic.PresentationFactory.createAutoPlayer
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
    private val _code = MutableStateFlow<String?>(null)
    val code = _code.asStateFlow()
    companion object {
        private const val POINTER_I = "i"
        private const val POINTER_J = "j"
        private const val POINTER_J_MINUS_1 = "j-1"
    }

    private val pointers = listOf(POINTER_I, POINTER_J, POINTER_J_MINUS_1)

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
                    arrayController.changeElementColor(i, color.iPointerLocation)
                    arrayController.changeCellColorUpTo(sortedUpTo,color.sortedPortionColor)
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

    fun onReset() {
        autoPlayer.dismiss()
        _createController()

    }


    private fun _createController() {
        arrayController.value = VisualArrayFactory.createController(
            itemLabels = list.value.map { it.toString() },
            pointersLabel = pointers
        )
        simulator = DiContainer.createSimulator(DataModel(array = list.value))
    }


}