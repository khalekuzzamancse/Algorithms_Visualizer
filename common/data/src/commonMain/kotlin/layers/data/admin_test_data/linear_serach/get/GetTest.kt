package layers.data.admin_test_data.linear_serach.get

import kotlinx.coroutines.runBlocking
import layers.data.TutorialCRUD
import layers.data.components.AlgoName

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