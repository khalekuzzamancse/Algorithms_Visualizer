package feature.search.ui.visulizer.controller

import androidx.compose.ui.graphics.Color
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController
import feature.search.MyPackagePrivate
import feature.search.ui.visulizer.contract.AlgoState
import feature.search.ui.visulizer.contract.AlgoVariablesState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import layers.ui.common_ui.Variable

@MyPackagePrivate
internal class VisualizationController<T : Any>(
    list: List<T>,
    private val visitedCellColor: Color,
    cellSizePx: Float,
    private val target: T,
) {
    val arrayController = ArrayController(list = list, cellSizePx = cellSizePx)
    val searcher = AlgoControllerImpl(list = list, target = target)


    val currentIndex: Flow<Int?> = searcher.algoState.map { it.currentIndex }
    val variables: Flow<List<AlgoVariablesState>> = searcher.algoState.map{it.toVariablesState()}

    val pseudocode = searcher.pseudocode
    private val _showPseudocode = MutableStateFlow(true)
    val showPseudocode = _showPseudocode.asStateFlow()
    fun togglePseudocodeVisibility() {
        _showPseudocode.update { !it }
    }

    init {
        CoroutineScope(Dispatchers.Default).launch {
            currentIndex.collect { index ->
                arrayController.changeCellColor(
                    index = index,
                    color = visitedCellColor
                )
            }
        }
    }



}