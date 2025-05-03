@file:Suppress("functionName")

package linearsearch.ui.ui
import core_ui.ArrayColor
import core_ui.GlobalMessenger
import core_ui.core.array.VisualArrayFactory
import core_ui.core.array.controller.VisualArrayController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import linearsearch.DiContainer
import linearsearch.domain.model.DataModel
import linearsearch.domain.model.SimulationState
import linearsearch.domain.service.PseudocodeGenerator
import linearsearch.domain.service.Simulator
import linearsearch.ui.presentationlogic.PresentationFactory.createAutoPlayer


internal class SimulationViewModel {
    private val _inputMode = MutableStateFlow(true)
    private val _array = MutableStateFlow(listOf(10, 5, 4, 13, 8))
    private val list = _array.asStateFlow()
    private lateinit var simulator: Simulator<Int>
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
    }


    fun onNext() {
        arrayController.value?.let { arrayController ->
            if(simulator.isFinished()){
                _onFinished()
                return
            }
            val state = simulator.next()
            _code.update { state.code }

            when (state) {
                is SimulationState.PointerI -> {
                    arrayController.movePointer(label = POINTER_I, index = state.index)
                }

                is SimulationState.FoundAt -> {
                    //Target is to found the index so make sense to highlight the cell instead of element
                    arrayController.changeCellColor(state.index, color = ArrayColor.FOUND_ELEMENT_COLOR)
                }
                is SimulationState.Finished ->_onFinished()
                else -> {

                }
            }
        }


    }

    fun onReset() {
         autoPlayer.dismiss()
        _createController()
        _code.update { PseudocodeGenerator.rawCode }

    }

    private fun _createController() {
        arrayController.value = VisualArrayFactory.createController(
            itemLabels = list.value.map { it.toString() },
            pointersLabel = pointers
        )
        simulator = DiContainer.createSimulator(DataModel(array = list.value, target))
    }



    private fun _onFinished(){
        autoPlayer.dismiss()//clear if auto play
        GlobalMessenger.updateAsEndedMessage()
        arrayController.value?.removePointers(listOf(POINTER_I))
    }


}