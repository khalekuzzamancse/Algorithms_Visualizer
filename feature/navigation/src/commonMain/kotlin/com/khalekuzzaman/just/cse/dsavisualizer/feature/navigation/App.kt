package com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.compose.AppTheme
import com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation.home.HomeDestination
import com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation.home.dashboard.Destination
import feature.search.ui.LinearSearchRoute


@Composable
fun MyApplication() {
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
                    else -> gotoHome()
                }

            }
        }


    }

    //BubbleSortSimulator()
    //InsertionSortSimulator()
    // SelectionSortSimulator()

    // QuizListScreen()
//QuizDemo()

}