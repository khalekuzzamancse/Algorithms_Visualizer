package feature.search.ui.route

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import feature.search.ui.visulizer.controller.LinearSearchSequence
import layers.ui.common_ui.common.decorators.BackArrowDecorator
import layers.ui.common_ui.ui.custom_navigation_item.NavigationItemInfo
import layers.ui.common_ui.common.ui.custom_navigation_item.VerticalListNavigation
import layers.ui.common_ui.ui.layout.TwoPaneLayout
import layers.ui.common_ui.ui.layout.WindowMode


@Composable
fun LinearSearchDestination() {
    VisualizationRoute()

//    val destinationViewModel = remember { DestinationViewModel() }
//    val selected = destinationViewModel.selectedDestinationIndex.collectAsState().value
//    var currentMode by remember { mutableStateOf(WindowMode.Compact) }
//    val navigationModifier =
//        if (currentMode == WindowMode.Compact) Modifier.fillMaxWidth() else Modifier.width(
//            IntrinsicSize.Max
//        )
//
//    TwoPaneLayout(
//        leftPane = {
//            VerticalListNavigation(
//                modifier = navigationModifier.fillMaxHeight().background(MaterialTheme.colorScheme.secondary),
//                destinations = destinationViewModel.sections.map {
//                    NavigationItemInfo(label = it, unFocusedIcon = Icons.Default.TextFormat)
//                },
//                onDestinationSelected = destinationViewModel::onDestinationSelected,
//                selectedDestinationIndex = selected,
//            )
//        },
//        topOrRightPane = {
//            Box(Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
//                when (selected) {
//                    0 -> {
//                        BackArrowDecorator(onBackRequest = destinationViewModel::onBackRequest) {
//                            TheorySection()
//                        }
//
//                    }
//                    1 -> {
//                        BackArrowDecorator(onBackRequest = destinationViewModel::onBackRequest) {
//                            StepsSection()
//                        }
//
//                    }
//                    2 -> {
//                        BackArrowDecorator(onBackRequest = destinationViewModel::onBackRequest) {
//                            Implementation()
//                        }
//
//                    }
//                    3 -> {
//                        BackArrowDecorator(onBackRequest = destinationViewModel::onBackRequest) {
//                            VisualizationRoute()
//                        }
//
//                    }
//                }
//            }
//
//        },
//        showTopOrRightPane = selected >= 0,
//        secondaryPaneAnimationState = selected,
//        onCurrentMode = {
//            currentMode = it
//        }
//    )


}


