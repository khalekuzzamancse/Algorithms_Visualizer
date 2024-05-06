package selection_sort.ui

import androidx.compose.ui.graphics.Color
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.swappable.SwappableArrayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import selection_sort.di.Factory


internal class UIController<T : Comparable<T>>(val list: List<T>) {
    val arrayController = SwappableArrayController(list = list)
    val algoController = Factory.createAlgoSimulator(list)

    val i = algoController.algoState.map { it.i }
    val minIndex = algoController.algoState.map { it.minIndex }
    private val swapPair = algoController.algoState.map { it.swappablePair }


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