package com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import feature.search.ui.route.LinearSearchDestination


@Composable
fun MyApplication() {
    AppTheme {
        Box(Modifier.padding(start=8.dp,end=8.dp)){
            LinearSearchDestination()
        }
    }

    //BubbleSortSimulator()
    //InsertionSortSimulator()
    // SelectionSortSimulator()

    // QuizListScreen()
//QuizDemo()

}