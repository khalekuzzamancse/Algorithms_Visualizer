package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.destination.compact_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.custom_navigation_item.NavigationItemInfo
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.custom_navigation_item.VerticalListNavigation
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.layout.TwoPaneLayout
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.layout.WindowMode
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.destination.BackArrowDecorator
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.destination.ViewModel
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.other.LinearSearch
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.other.TheorySection
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.other.tutorial.ImplementationPreview
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.other.tutorial.StepsSectionPreview


@Composable
fun LinearSearchCompactScreen() {
    val viewModel = remember { ViewModel() }
    val selected = viewModel.selectedDestinationIndex.collectAsState().value
    var currentMode by remember { mutableStateOf(WindowMode.Compact) }
    val navigationModifier =
        if (currentMode == WindowMode.Compact) Modifier.fillMaxWidth() else Modifier.width(
            IntrinsicSize.Max
        )

    TwoPaneLayout(
        leftPane = {
            VerticalListNavigation(
                modifier = navigationModifier.fillMaxHeight().background(MaterialTheme.colorScheme.secondary),
                destinations = viewModel.sections.map {
                    NavigationItemInfo(label = it, unFocusedIcon = Icons.Default.TextFormat)
                },
                onDestinationSelected = viewModel::onDestinationSelected,
                selectedDestinationIndex = selected,
            )
        },
        topOrRightPane = {
            Box(Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
                when (selected) {
                    0 -> {
                        BackArrowDecorator(onBackRequest = viewModel::onBackRequest) {
                            TheorySection()
                        }

                    }
                    1 -> {
                        BackArrowDecorator(onBackRequest = viewModel::onBackRequest) {
                            StepsSectionPreview()
                        }

                    }
                    2 -> {
                        BackArrowDecorator(onBackRequest = viewModel::onBackRequest) {
                            ImplementationPreview()
                        }

                    }
                    3 -> {
                        BackArrowDecorator(onBackRequest = viewModel::onBackRequest) {
                            LinearSearch()
                        }

                    }
                }
            }

        },
        showTopOrRightPane = selected >= 0,
        secondaryPaneAnimationState = selected,
        onCurrentMode = {
            currentMode = it
        }
    )


}


