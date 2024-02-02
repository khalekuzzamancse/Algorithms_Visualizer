package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.admin_test_data.linear_serach.add

import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.AlgoName
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.TutorialCRUD
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.steps.Steps
import kotlinx.coroutines.runBlocking

private val steps= listOf(
"Step 1: Start",
"Step 2: Initialize the search key",
"Step 3: Begin iterating through the array",
"Step 4: Compare the current element with the search key",
"Step 5: If a match is found, return the index",
"Step 6: If the end of the array is reached and no match is found, return -1",
"Step 7: End"
)
fun main() {
    runBlocking {
        val data=Steps(
            algoName = AlgoName.LinearSearch,
            steps= steps
        )
        TutorialCRUD().addStep(data)
    }
}