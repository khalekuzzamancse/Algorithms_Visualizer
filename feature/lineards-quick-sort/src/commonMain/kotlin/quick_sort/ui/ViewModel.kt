package quick_sort.ui

import core_ui.core.array.controller.VisualArrayController
import core_ui.core.controller.ControllerFactory.createAutoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import quick_sort.data.QuickSortIterator
import quick_sort.data.QuickSortState

class ViewModel {
    private val _inputMode = MutableStateFlow(true)
    private val _array = MutableStateFlow(listOf(10, 5, 4, 13, 8))
    private val list = _array.asStateFlow()
    private lateinit var arraySimulator: ArraySimulator
    private lateinit var iterator: QuickSortIterator

    private val _code = MutableStateFlow<String?>(null)
    val code = _code.asStateFlow()

    val inputMode = _inputMode.asStateFlow()
    val arrayController = MutableStateFlow<VisualArrayController?>(null)
    //Will be used for reset that is why observable

    val autoPlayer = createAutoPlayer(::onNext)

    init {
        autoPlayer.onNextCallback = ::onNext
    }

    fun onInputComplete(inputData: List<Int>) {
        _array.update { inputData }
        _inputMode.update { false }
        createController()
    }


    fun onReset() {
        autoPlayer.dismiss()
        createController()

    }


    private fun createController() {
        arraySimulator = ArraySimulator.create(list.value)
        arrayController.value = arraySimulator.arrayController
        iterator = QuickSortIterator.create(_array.value)
    }
    fun onNext() {
        val state = iterator.next()
        if (state is QuickSortState.State) {
            arraySimulator.movePointerI(state.i)
            arraySimulator.movePointerJ(state.j)
            arraySimulator.onPivotChange(state.pivot)
            arraySimulator.onSubArrayChange(state.low, state.high)
            state.swapped?.let { (i, j) ->
                arraySimulator.swap(i, j)
            }
        }
        if(state is QuickSortState.Finish)
            arraySimulator.clearPointer()
    }


}