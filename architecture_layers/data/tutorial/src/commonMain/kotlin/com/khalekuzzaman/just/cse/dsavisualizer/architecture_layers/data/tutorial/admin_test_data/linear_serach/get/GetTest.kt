package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.admin_test_data.linear_serach.get

import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.AlgoName
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.TutorialCRUD
import kotlinx.coroutines.runBlocking

fun main() {
    val algoName = AlgoName.LinearSearch
    runBlocking {
//        TutorialCRUD().getTheory(algoName).collect{
//            println("LinearSearchGetTest:getTheory()::$it")
//        }
//        TutorialCRUD().getSteps(algoName).collect{
//            println("LinearSearchGetTest:getSteps()::$it")
//        }
//        TutorialCRUD().getPseudocode(algoName).collect{
//            println("LinearSearchGetTest:getPseudocode()::$it")
//        }
        TutorialCRUD().getImplementation(algoName).collect {
            println("LinearSearchGetTest:getImplementation()::$it")
        }
    }

}