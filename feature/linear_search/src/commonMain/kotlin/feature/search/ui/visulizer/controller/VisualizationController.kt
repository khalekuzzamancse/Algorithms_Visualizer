package feature.search.ui.visulizer.controller

import androidx.compose.ui.graphics.Color
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController
import feature.search.MyPackagePrivate
import feature.search.ui.visulizer.model.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@MyPackagePrivate
internal class VisualizationController<T : Any>(
    list: List<T>,
    private val visitedCellColor: Color,
    cellSizePx: Float,
    target: T,
) {
    val arrayController = ArrayController(list = list, cellSizePx = cellSizePx)
    val searcher = LinearSearchSequence(list = list, target = target)

    val pointerIndex: Flow<Int?> = searcher.state.map { it.currentIndex }

    val pseudocode = searcher.pseudocode
    private val _showPseudocode = MutableStateFlow(false)
    val showPseudocode = _showPseudocode.asStateFlow()
    fun togglePseudocodeVisibility() {
        _showPseudocode.update { !it }
    }

    init {
        CoroutineScope(Dispatchers.Default).launch {
            pointerIndex.collect { index ->
                arrayController.changeCellColor(
                    index = index,
                    color = visitedCellColor
                )
            }
        }
    }


}