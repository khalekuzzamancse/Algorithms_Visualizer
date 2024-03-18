package feature.search.ui.visulizer.controller

import androidx.compose.ui.graphics.Color
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController
import feature.search.ui.visulizer.contract.AlgoPseudocode
import feature.search.ui.visulizer.contract.AlgoVariablesState
import feature.search.ui.visulizer.contract.SimulationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


internal class UIController<T : Comparable<T>>(
    val list: List<T>, val cellSizePx: Float, target: T,
    private val visitedCellColor: Color
) {
    val arrayController = ArrayController(list = list, cellSizePx = cellSizePx)
    val algoController = AlgoControllerImpl(list = list, target = target)

    val currentIndex: Flow<Int?> = algoController.algoState.map { state ->
        if (state is SimulationState.AlgoState<*>)
            state.currentIndex
        else null
    }





    val pseudocode = algoController.pseudocode
    private val _showPseudocode = MutableStateFlow(true)
    val showPseudocode = _showPseudocode.asStateFlow()
    fun togglePseudocodeVisibility() {
        _showPseudocode.update { !it }
    }

    init {
        CoroutineScope(Dispatchers.Default).launch {
            currentIndex.collect(::markCellAsVisited)
        }
    }

    private fun markCellAsVisited(index: Int?) {
        arrayController.changeCellColor(
            index = index,
            color = visitedCellColor
        )
    }


}