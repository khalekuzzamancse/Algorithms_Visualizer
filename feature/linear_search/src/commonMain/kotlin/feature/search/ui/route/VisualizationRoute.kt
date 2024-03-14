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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.Array
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.pointer.CellPointerComposable2
import feature.search.MyPackagePrivate
import feature.search.ui.visulizer.contract.AlgoVariablesState
import feature.search.ui.visulizer.controller.VisualizationController
import feature.search.ui.visulizer.contract.Pseudocode
import layers.ui.common_ui.ControlSection
import layers.ui.common_ui.Variable
import layers.ui.common_ui.VariablesSection
import layers.ui.common_ui.common.pseudocode.PseudoCodeExecutor
import layers.ui.common_ui.common.pseudocode.CodeLine

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
            target = 60
        )
    }
    val arrayController = controller.arrayController
    val searcher = controller.searcher
    val currentIndex = controller.currentIndex.collectAsState(null).value


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
        ArraySection(list, cellSize, arrayController, currentIndex)
        Column {
            _VariableSection(controller.variables.collectAsState(emptyList()).value)
            Spacer(Modifier.height(64.dp))
            AnimatedVisibility(controller.showPseudocode.collectAsState().value) {
                PseudoCodeSection(controller.pseudocode.collectAsState().value)
            }

        }

    }


}

@Composable
private fun _VariableSection(
    variables: List<AlgoVariablesState>
) {
    VariablesSection(variables.map {
        Variable(it.name, it.value)
    })

}

@Composable
private fun PseudoCodeSection(
    code: List<Pseudocode.Line>
) {
    PseudoCodeExecutor(
        modifier = Modifier.padding(8.dp),
        code = code.map {
            CodeLine(line = it.line, highLighting = it.highLighting, lineNumber = it.lineNumber)
        }
    )
}

@Composable
private fun <T> ArraySection(
    list: List<T>,
    cellSize: Dp,
    arrayController: ArrayController<T>,
    currentIndex: Int?
) {
    Box {
        Array(
            cellSize = cellSize,
            arrayController = arrayController,
            enableDrag = false
        )
        currentIndex?.let { index ->
            if (index >= 0 && index < list.size) {
                CellPointerComposable2(
                    cellSize = cellSize,
                    position = arrayController.cells.value[index].position,
                    label = "i"
                )
            }

        }

    }

}