package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.admin_test_data.linear_serach.add

import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.AlgoName
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.TutorialCRUD
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.pseudocode.Pseudocode
import kotlinx.coroutines.runBlocking

private val pseudocode = """
        function linearSearch(arr, key):
            for i from 0 to length(arr) - 1:
                if arr[i] equals key:
                    return i 
            return -1 
    """.trimIndent()
fun main() {
    runBlocking {
        val data = Pseudocode(
            algoName = AlgoName.LinearSearch,
            code = pseudocode
        )
        TutorialCRUD().addPseudocode(data)
    }
}