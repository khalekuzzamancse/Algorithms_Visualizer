package binary_search.ui

import androidx.compose.ui.graphics.Color
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import layers.ui.common_ui.decorators.tab_layout.TabDecoratorController
import binary_search.di.Factory
import binary_search.domain.AlgoStateController
import binary_search.domain.Pseudocode
import binary_search.domain.VisualizationState

/**
 * @param tabController used to controller the tab navigation and preserve the state on ...
 */

internal class BinarySearchViewModel<T : Comparable<T>>(
    val cellSizePx: Float,
    private val visitedCellColor: Color,
    val tabController: TabDecoratorController,
) {

    private val _elements = MutableStateFlow(listOf(10, 20, 30, 40, 50, 60).sorted())
    private val _target = MutableStateFlow(50)
    private val _isInputMode = MutableStateFlow(true)
    private lateinit var _algoController: AlgoStateController<Int>

    private val _arrayController =
        MutableStateFlow(ArrayController(list = _elements.value, cellSizePx = cellSizePx))
    val arrayController = _arrayController.asStateFlow()
    val elements = _elements.asStateFlow()
    val isInputMode = _isInputMode.asStateFlow()

    // PRIVATE
    private val _low = MutableStateFlow<Int?>(null)
    private val _high = MutableStateFlow<Int?>(null)
    private val _mid = MutableStateFlow<Int?>(null)
    private val _pseudocode = MutableStateFlow<List<Pseudocode.Line>>(emptyList())
    private val _endState = MutableStateFlow<VisualizationState.Finished?>(null)


    //PUBLIC
    val low = _low.asStateFlow()
    val high = _high.asStateFlow()
    val mid = _mid.asStateFlow()
    val endState = _endState.asStateFlow()
    val pseudocode = _pseudocode.asStateFlow()
    //


    /*
    Used by when the input dialogue is dismissed
     */
    //METHODS SECTION
    fun onInputCompleted() {
        _isInputMode.update { false }
    }

    fun onInputCompleted(elements: List<Int>, target: Int) {
        _elements.update { elements.sorted() }//if by mistake user input un sorted array
        _target.update { target }
        _arrayController.update { ArrayController(list = _elements.value, cellSizePx = cellSizePx) }
        //using factory method so do not need to pass as dependency because this same as dependency injection
        //if the algoController need change to different implementation,then return different from the factory
        //the client does not need to change  or worry
        _isInputMode.update { false }
        _algoController = Factory.createAlgoController(_elements.value, _target.value)
        observe() //Almost Non Blocking call
      changeCellOnVisited() //Almost Non Blocking call

    }



    private fun observe() {
        launch {
            _algoController.algoState.collect { state ->
                _low.value = if (state is VisualizationState.AlgoState<*>) state.low else null
            }
        }
        launch {
            _algoController.algoState.collect { state ->
                _high.value = if (state is VisualizationState.AlgoState<*>) state.high else null
            }
        }
        launch {
            _algoController.algoState.collect { state ->
                _mid.value = if (state is VisualizationState.AlgoState<*>) state.mid else null
            }
        }
        launch {
            _algoController.pseudocode.collect { code ->
                _pseudocode.value = code
            }
        }
        launch {
            _algoController.algoState.collect { state ->
                _endState.value = if (state is VisualizationState.Finished) state else null
            }

        }
    }
    private fun changeCellOnVisited(){
        launch { mid.collect(::markCellAsVisited)  }
    }


    private fun launch(block: suspend () -> Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            block()
        }
    }


    private fun markCellAsVisited(index: Int?) {
        _arrayController.value.changeCellColor(
            index = index,
            color = visitedCellColor
        )
    }

    fun onNext() {
        if (_algoController.hasNext()) {
          _algoController.next()
        }

    }


}