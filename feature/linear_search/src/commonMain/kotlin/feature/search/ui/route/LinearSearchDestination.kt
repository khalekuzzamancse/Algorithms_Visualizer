package feature.search.ui.route

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import feature.search.ui.visulizer.controller.UIController
import feature.search.ui.visulizer.VisualizationRoute
import layers.ui.common_ui.dialogue.InputListDialog
import layers.ui.common_ui.decorators.TabDestination
import layers.ui.common_ui.decorators.TabLayoutDecorator
import platform_contracts.WebPageLoader


@Composable
fun LinearSearchDestination(
) {

    var inputMode by remember { mutableStateOf(true) }
    var list by remember { mutableStateOf(listOf(10, 20, 30, 40, 50)) }
    var target by remember { mutableStateOf(50) }

    if (inputMode) {
        InputListDialog(
            showDialog = true,
            onDismiss = { inputMode = false }) { array, tar ->
            list = array
            target = tar
            inputMode = false

        }
    } else {
        _SearchDestination(
            list = list, target = target
        )

    }


}

@Composable
private fun <T : Comparable<T>> _SearchDestination(
    list: List<T>,
    target: T
) {
    val cellSize = 64.dp
    val sizePx = with(LocalDensity.current) { cellSize.toPx() }
    val visitedCellColor = MaterialTheme.colorScheme.secondaryContainer

    var controller by remember {
        mutableStateOf(
            UIController(
                list = list,
                cellSizePx = sizePx,
                target = target,
                visitedCellColor = visitedCellColor
            )
        )
    }

    var destination by remember { mutableStateOf(TabDestination.Visualization) }
    TabLayoutDecorator(
        onClickTab = {
            destination = it
        },
        content = {
            Column(Modifier.fillMaxWidth()) {
                AnimatedContent(destination) { selected ->
                    when (selected) {
                        TabDestination.Visualization -> {
                            VisualizationRoute(
                                modifier = Modifier.padding(top = 16.dp),
                                cellSize = cellSize,
                                uiController = controller,
                                onResetRequest = {
                                    controller = UIController(
                                        list = list,
                                        cellSizePx = sizePx,
                                        target = target,
                                        visitedCellColor = visitedCellColor,

                                    )
                                },
                                onAutoPlayRequest = {
                                    //Todo Implement the feature later
                                }
                            )
                        }

                        TabDestination.Theory -> {
                            WebPageLoader(url = "https://khalekuzzamancse.github.io/documentations/docs/quick_sort/theory.html")

                        }

                        TabDestination.ComplexityAnalysis -> {
                            WebPageLoader(url = "https://khalekuzzamancse.github.io/documentations/docs/quick_sort/complexity_analysis.html")

                        }

                        TabDestination.Pseudocode -> {
                            WebPageLoader(url = "https://khalekuzzamancse.github.io/documentations/docs/quick_sort/steps_n_pseucode.html")

                        }

                        TabDestination.Implementation -> {
                            WebPageLoader(url = "https://khalekuzzamancse.github.io/documentations/docs/quick_sort/implementaion.html")
                        }

                    }
                }

            }
        }
    )

}

