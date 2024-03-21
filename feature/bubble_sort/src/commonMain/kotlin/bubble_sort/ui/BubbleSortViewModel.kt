package bubble_sort.ui

import androidx.compose.ui.graphics.Color
import bubble_sort.di.Factory
import bubble_sort.domain.AlgoStateController
import bubble_sort.domain.Pseudocode
import bubble_sort.domain.SwappedPair
import bubble_sort.domain.VisualizationState
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.swappable.SwappableArrayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


internal class BubbleSortViewModel<T : Comparable<T>> {
    private val _elements = MutableStateFlow(listOf(10, 5, 4, 13, 8))
    private val _isInputMode = MutableStateFlow(true)
    private lateinit var _algoController: AlgoStateController<Int>
    private val _arrayController =
        MutableStateFlow(SwappableArrayController(list = _elements.value))

    //
    val arrayController = _arrayController.asStateFlow()
    val isInputMode = _isInputMode.asStateFlow()
    val list = _elements.asStateFlow()
    private val _i = MutableStateFlow<Int?>(null)
    private val _j = MutableStateFlow<Int?>(null)
    val i = _i.asStateFlow()
    val j = _j.asStateFlow()

    private val _swappablePair = MutableStateFlow<SwappedPair<T>?>(null)
    val swapPair = _swappablePair.asStateFlow()

    private val _pseudocode = MutableStateFlow<List<Pseudocode.Line>>(emptyList())
    val pseudocode=_pseudocode.asStateFlow()
    /*
     Used by when the input dialogue is dismissed
      */

    fun onInputComplete(elements: List<Int>) {
        _algoController = Factory.createAlgoController(_elements.value)
        _elements.update { elements }
        _arrayController.update { SwappableArrayController(list = elements) }
        //using factory method so do not need to pass as dependency because this same as dependency injection
        //if the algoController need change to different implementation,then return different from the factory
        //the client does not need to change  or worry
        _isInputMode.update { false }
        observeState()
        onArrayStateChanged()
    }


    fun onNext() {
        if (_algoController.hasNext()) {
            _algoController.next()
        }

    }


    private fun observeState() {
        launch {
            _algoController.algoState.collect { state ->
                _i.value = if (state is VisualizationState.AlgoState<*>) state.i else null
                _j.value = if (state is VisualizationState.AlgoState<*>) state.j else null
                if (state is VisualizationState.AlgoState<*>) {
                    val pair = state.swappablePair
                    if (pair != null) {
                        try {
                            _swappablePair.update {
                                SwappedPair(
                                    j = pair.j,
                                    jPlus1 = pair.jPlus1,
                                    jValue = pair.jValue as T,
                                    jPlus1Value = pair.jPlus1Value as T
                                )

                            }
                        } catch (_: Exception) {
                        }
                    }

                }
            }
        }
        launch {
            _algoController.pseudocode.collect{
                println(it)
                _pseudocode.value=it
            }
        }
    }

    private fun launch(block: suspend () -> Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            block()
        }
    }

    private fun onArrayStateChanged() {
        launch { i.collect(::markCellAsVisited) }
        launch {
            swapPair.collect { pair ->
                if (pair != null) {
                    arrayController.value.swapElement(pair.j, pair.jPlus1)
                }
            }
        }
    }


    private fun markCellAsVisited(index: Int?, color: Color = Color.Red) {
        arrayController.value.changeCellColor(
            index = index,
            color = color
        )
    }


}