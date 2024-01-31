package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.destination.compact_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.custom_navigation_item.NavigationItemInfo
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.navigation.bottom_navigation.BottomBarDecorator
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.destination.ViewModel
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.LinearSearch
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.tutorial.ImplementationPreview
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.tutorial.StepsSectionPreview
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.tutorial.TutorialTheoryPreview

@Composable
fun LinearSearchCompactScreen() {
    val viewModel = remember { ViewModel() }
    val selected=viewModel.selectedDestinationIndex.collectAsState().value
    BottomBarDecorator(
        bottomDestinations = viewModel.sections.map {
            NavigationItemInfo(label = it, unFocusedIcon = Icons.Default.TextFormat)
        },
        onDestinationSelected = viewModel::onDestinationSelected,
        selectedDestinationIndex = selected,
        topAppbar = {}
    ) {
        AnimatedContent(selected){selected->
            when(selected){
                0-> TutorialTheoryPreview()
                1-> StepsSectionPreview()
                2-> ImplementationPreview()
                3-> LinearSearch()
            }
        }

    }
}