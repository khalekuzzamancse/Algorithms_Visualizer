@file:Suppress("functionName")

package linearsearch.ui

import linearsearch.di.DiContainer
import linearsearch.domain.model.DataModel
import linearsearch.domain.model.SimulationState
import linearsearch.domain.service.Simulator
import linearsearch.presentationlogic.PresentationFactory.createAutoPlayer
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
    }

    private val pointers = listOf(POINTER_I)

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
        observeCode()
    }

    private fun observeCode() {
        scope.launch {

        }
    }

    fun onNext() {
        arrayController.value?.let { arrayController ->
            val state = simulator.next()
            _code.update { state.code }

            when (state) {

                is SimulationState.PointerI -> {
                    arrayController.movePointer(label = POINTER_I, index = state.index)
                }

                is SimulationState.FoundAt -> {
                    arrayController.changeElementColor(index = state.index, color = color.foundAt)
                }

                is SimulationState.Finished -> {
                    arrayController.removePointers(listOf(POINTER_I))

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