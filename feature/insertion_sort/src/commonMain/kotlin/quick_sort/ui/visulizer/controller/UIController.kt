package quick_sort.ui.visulizer.controller

import androidx.compose.ui.graphics.Color
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.swappable.SwappableArrayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import quick_sort.ui.visulizer.contract.AlgoVariablesState


internal class UIController<T : Comparable<T>>(val list: List<T>) {
    val arrayController = SwappableArrayController(list = list)
    val algoController = AlgoControllerImpl(list = list)

    val i = algoController.algoState.map { it.i }
    val j = algoController.algoState.map { it.j}
     val shiftedIndex = algoController.algoState.map { it.shiftedIndex }
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
            algoController.algoState.collect {
                if (it.keyFinalPosition!=null&&it.key!=null){
                    arrayController.changeElementValue(it.keyFinalPosition,it.key)
                }
                if (it.shiftedIndex!=null){
                    arrayController.copyElement(it.shiftedIndex,it.shiftedIndex+1)
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