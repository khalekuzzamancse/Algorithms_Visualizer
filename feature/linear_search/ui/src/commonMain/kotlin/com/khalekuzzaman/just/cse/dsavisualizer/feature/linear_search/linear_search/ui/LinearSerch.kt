package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.linear_search.ui.viewmodel.LinearSearchViewModel
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.Array
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.pointer.CellPointerComposable2
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.PseudoCodeLine
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.controls.ControlSection

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LinearSearch() {
    val list = listOf(10, 20, 30, 40, 50)
    val cellSize = 64.dp
    val sizePx = with(LocalDensity.current) { cellSize.toPx() }
    val visitedCellColor =Color.Red
    val viewModel = remember {
        LinearSearchViewModel(
            list = list,
            visitedCellColor = visitedCellColor,
            cellSizePx = sizePx,
            target = 60
        )
    }
    val arrayManager =viewModel.arrayManager
    val searcher =viewModel.searcher
    val state = searcher.state
    val pointerIndex = viewModel.pointerIndex.collectAsState().value


    FlowRow(Modifier.verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement =Arrangement.spacedBy(16.dp)
    ) {
        ControlSection(
            onNext = searcher::next
        )
        Spacer(Modifier.height(64.dp))
        Box {
            Array(
                cellSize = cellSize,
                arrayManager = arrayManager
            )
            pointerIndex?.let {
                CellPointerComposable2(
                    cellSize = cellSize,
                    position = arrayManager.cells.value[pointerIndex].position,
                    label = "i"
                )
            }

        }
        Column {
            VariablesSection(
                currentElement = state.collectAsState().value.currentElement,
                isMatched = state.collectAsState().value.isMatched,
                currentIndex = state.collectAsState().value.currentIndex,
            )
            Spacer(Modifier.height(64.dp))
            TextCodeLine(viewModel.pseudocode.collectAsState().value)
        }

    }


}

@Composable
fun TextCodeLine(code: List<PseudoCodeLine>) {
    Column {
        code.forEach {
            Text(
                text = it.line,
                color = if (it.highLighting) Color.Red else Color.Unspecified
            )
        }

    }

}