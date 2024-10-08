package feature.search.ui

import androidx.compose.ui.graphics.Color
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController
import feature.search.di.Factory
import feature.search.domain.AlgoStateController
import feature.search.domain.Pseudocode
import feature.search.domain.VisualizationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import layers.ui.common_ui.decorators.tab_layout.TabDecoratorController

/**
 * @param tabController used to controller the tab navigation and preserve the state on ...
 */

internal class LinearSearchViewModel<T : Comparable<T>>(
    val cellSizePx: Float,
    private val visitedCellColor: Color,
    val tabController: TabDecoratorController,
) {

    private val _elements = MutableStateFlow(listOf(10, 20, 30, 40, 50, 60))
    private val _target = MutableStateFlow(50)
    private val _isInputMode = MutableStateFlow(true)
    private lateinit var _algoController: AlgoStateController<Int>

    private val _arrayController =
        MutableStateFlow(ArrayController(list = _elements.value, cellSizePx = cellSizePx))
    val arrayController = _arrayController.asStateFlow()
    val elements = _elements.asStateFlow()
    val isInputMode = _isInputMode.asStateFlow()

    /*
    Used by when the input dialogue is dismissed
     */


    fun onInputCompleted(elements: List<Int>, target: Int) {
        _elements.update { elements }
        _target.update { target }
        _arrayController.update { ArrayController(list = _elements.value, cellSizePx = cellSizePx) }
        //using factory method so do not need to pass as dependency because this same as dependency injection
        //if the algoController need change to different implementation,then return different from the factory
        //the client does not need to change  or worry
        _isInputMode.update { false }
        _algoController = Factory.createAlgoController(_elements.value, _target.value)
        observe()
    }


    /*
    ///
    ///
     */

    private val _currentIndex = MutableStateFlow<Int?>(null)
    val currentIndex = _currentIndex.asStateFlow()

    private val _endState = MutableStateFlow<VisualizationState.Finished?>(null)
    val endState = _endState.asStateFlow()
    private val _pseudocode = MutableStateFlow<List<Pseudocode.Line>>(emptyList())
    val pseudocode = _pseudocode.asStateFlow()



    private fun observe() {
        CoroutineScope(Dispatchers.Default).launch {
            _algoController.algoState.collect { state ->
                _currentIndex.value =
                    if (state is VisualizationState.AlgoState<*>) state.currentIndex else null
                _endState.value = if (state is VisualizationState.Finished) state else null
            }
        }
        CoroutineScope(Dispatchers.Default).launch {
            _algoController.pseudocode.collect { code ->
                _pseudocode.value = code
            }
        }
        CoroutineScope(Dispatchers.Default).launch {
            _currentIndex.collect(::markCellAsVisited)
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