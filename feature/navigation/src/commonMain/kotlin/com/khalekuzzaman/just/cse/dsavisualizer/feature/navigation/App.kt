package com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.compose.AppTheme
import com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation.home.dashboard.Destination
import binary_search.ui.BinarySearchRoute
import bubble_sort.ui.BubbleSortRoute
import com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation.home.HomeDestination
import feature.search.ui.LinearSearchRoute


@Composable
fun MyApplication() {
    Home()
   // BubbleSortRoute()
    //BubbleSortSimulator()
    //InsertionSortSimulator()
    // SelectionSortSimulator()


}

@Composable
private fun Home() {
    var destination by remember { mutableStateOf(Destination.None) }
    val gotoHome: () -> Unit = {
        destination = Destination.None
    }
    val navigateTo: (Destination) -> Unit = {
        destination = it
    }
    AppTheme {
        if (destination == Destination.None) {
            HomeDestination(onNavigationRequest = navigateTo)
        } else {
            AnimatedContent(destination) { selected ->
                when (selected) {
                    Destination.LinearSearch -> LinearSearchRoute(onExitRequest = gotoHome)
                    Destination.BinarySearch -> BinarySearchRoute(onExitRequest = gotoHome)

                    else -> gotoHome()
                }

            }
        }


    }

}