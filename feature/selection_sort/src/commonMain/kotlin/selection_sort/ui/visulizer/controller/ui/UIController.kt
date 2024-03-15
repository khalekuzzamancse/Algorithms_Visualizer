package selection_sort.ui.visulizer.controller.ui

import androidx.compose.ui.graphics.Color
import selection_sort.ui.visulizer.contract.AlgoVariablesState
import selection_sort.ui.visulizer.controller.AlgoControllerImpl
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.swappable.SwappableArrayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


internal class UIController<T : Comparable<T>>(val list: List<T>) {
    val arrayController = SwappableArrayController(list = list)
    val algoController = AlgoControllerImpl(list = list)

    val i = algoController.algoState.map { it.i }
    val minIndex = algoController.algoState.map { it.minIndex }
    private val swapPair = algoController.algoState.map { it.swappablePair }
    val variables: Flow<List<AlgoVariablesState>> =
        algoController.algoState.map { it.toVariablesState() }

    val pseudocode = algoController.pseudocode
    private val _showPseudocode = MutableStateFlow(true)
    val showPseudocode = _showPseudocode.asStateFlow()
    fun togglePseudocodeVisibility() {
        _showPseudocode.update { !it }
    }

    init {
        CoroutineScope(Dispatchers.Default).launch {
            i.collect(::markCellAsVisited)
        }
        CoroutineScope(Dispatchers.Default).launch {
            swapPair.collect { pair ->
                if (pair != null) {
                    arrayController.swapElement(pair.i, pair.minIndex)
                }
            }
        }
    }

    private fun markCellAsVisited(index: Int?, color: Color = Color.Red) {
        arrayController.changeCellColor(
            index = index,
            color = color
        )
    }


}