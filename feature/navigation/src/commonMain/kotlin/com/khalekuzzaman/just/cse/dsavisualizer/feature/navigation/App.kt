package com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import binary_search.ui.BinarySearchRoute
import bubble_sort.ui.BubbleSortRoute
import com.example.compose.AppTheme
import com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation.home.dashboard.Destination
import binary_search.ui.BinarySearchRoute


@Composable
fun MyApplication() {
    AppTheme {
        NavHost()
//
        //BubbleSortSimulator()
        //InsertionSortSimulator()
        // SelectionSortSimulator()
    }


}

@Composable
private fun NavHost() {
    var destination by remember { mutableStateOf(Destination.None) }
    val gotoHome: () -> Unit = {
        destination = Destination.None
    }
    val navigateTo: (Destination) -> Unit = {
        destination = it
    }
    if (destination == Destination.None) {
        HomeDestination(onNavigationRequest = navigateTo)
    } else {
        AnimatedContent(destination) { selected ->
            when (selected) {
                Destination.LinearSearch -> LinearSearchRoute(onExitRequest = gotoHome)
                Destination.BinarySearch -> BinarySearchRoute(onExitRequest = gotoHome)
                Destination.BubbleSort -> BubbleSortRoute(onExitRequest = gotoHome)
                else -> gotoHome()
            }

        }
    }


}