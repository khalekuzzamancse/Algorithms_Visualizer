package binary_search.ui.visualizer.controller.ui

import androidx.compose.ui.graphics.Color
import binary_search.ui.visualizer.contract.AlgoVariablesState
import binary_search.ui.visualizer.controller.AlgoControllerImpl
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


internal class UIController<T: Comparable<T>>(val list: List<T>, private val cellSizePx: Float, target: T) {
    val arrayController = ArrayController(list = list, cellSizePx = cellSizePx)
    val algoController = AlgoControllerImpl(list = list, target = target)

    val low = algoController.algoState.map { it.low }
    val high= algoController.algoState.map { it.high }
    val mid= algoController.algoState.map { it.mid }
    val variables: Flow<List<AlgoVariablesState>> = algoController.algoState.map { it.toVariablesState() }

    val pseudocode = algoController.pseudocode
    private val _showPseudocode = MutableStateFlow(true)
    val showPseudocode = _showPseudocode.asStateFlow()
    fun togglePseudocodeVisibility() {
        _showPseudocode.update { !it }
    }

    init {
        CoroutineScope(Dispatchers.Default).launch {
            low.collect(::markCellAsVisited)
        }
        CoroutineScope(Dispatchers.Default).launch {
            high.collect(::markCellAsVisited)
        }
        CoroutineScope(Dispatchers.Default).launch {
            mid.collect(::markCellAsVisited)
        }
    }
    private fun markCellAsVisited(index:Int?){
         val visitedCellColor = Color.Red
        arrayController.changeCellColor(
            index = index,
            color = visitedCellColor
        )
    }


}