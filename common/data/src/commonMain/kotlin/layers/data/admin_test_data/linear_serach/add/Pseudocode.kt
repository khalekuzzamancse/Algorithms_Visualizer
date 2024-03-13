package layers.data.admin_test_data.linear_serach.add

import kotlinx.coroutines.runBlocking
import layers.data.TutorialCRUD
import layers.data.components.AlgoName
import layers.data.components.Pseudocode

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