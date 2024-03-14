package feature.search.ui.route

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.Array
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.pointer.CellPointerComposable2
import feature.search.MyPackagePrivate
import feature.search.ui.visulizer.controller.VisualizationController
import layers.ui.common_ui.ControlSection
import layers.ui.common_ui.common.pseudocode.PseudoCodeExecutor
import layers.ui.common_ui.common.pseudocode.PseudoCodeLine

@OptIn(ExperimentalLayoutApi::class, MyPackagePrivate::class)
@Composable
internal fun VisualizationRoute() {
    val list = listOf(10, 20, 30, 40, 50)
    val cellSize = 64.dp
    val sizePx = with(LocalDensity.current) { cellSize.toPx() }
    val visitedCellColor = Color.Red
    val controller = remember {
        VisualizationController(
            list = list,
            visitedCellColor = visitedCellColor,
            cellSizePx = sizePx,
            target = 30
        )
    }
    val arrayManager = controller.arrayController
    val searcher = controller.searcher
    val pointerIndex = controller.pointerIndex.collectAsState(null).value

    FlowRow(
        Modifier.verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ControlSection(
            onNext = searcher::next,
            isCodeOff = controller.showPseudocode.collectAsState().value,
            onCodeVisibilityToggleRequest = controller::togglePseudocodeVisibility
        )
        Spacer(Modifier.height(64.dp))
        Box {
            Array(
                cellSize = cellSize,
                arrayController = arrayManager,
                enableDrag = false
            )
            pointerIndex?.let {index->
                if (index>=0&&index<list.size){
                    CellPointerComposable2(
                        cellSize = cellSize,
                        position = arrayManager.cells.value[index].position,
                        label = "i"
                    )
                }

            }

        }
        Column {
//            VariablesSection()
            Spacer(Modifier.height(64.dp))
            AnimatedVisibility(controller.showPseudocode.collectAsState().value) {
                PseudoCodeExecutor(
                    modifier = Modifier.padding(8.dp),
                    code = controller.pseudocode.collectAsState().value.map {
                        PseudoCodeLine(
                            line = it.line,
                            lineNumber = it.lineNumber,
                            highLighting = it.highLighting
                        )
                    }
                )
            }

        }

    }


}
