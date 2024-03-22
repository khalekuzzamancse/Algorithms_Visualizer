package bubble_sort.infrastructure

import kotlin.test.Test
import kotlin.test.assertTrue

class AlgoControllerImplTest {

    @Test
    fun `is sorted after all iteration`() {
        val unsortedList = listOf(4, 2, 5, 1, 3)
        val controller = AlgoControllerImpl(BubbleSortIterator(unsortedList))
        while (true) {
            if (!controller.hasNext())
                break
            else
                controller.next()

        }
        val isSorted=unsortedList.sorted()==controller.sortedList
        assertTrue(isSorted)
    }
    @Test
    fun `testing for sorted array`() {
        // Arrange
        val unsortedList = listOf(4,3,2,1,7,6,8,5)
        val controller = AlgoControllerImpl(BubbleSortIterator(unsortedList))
        while (true) {
            if (!controller.hasNext())
                break
            else{
                controller.next()
                println(controller.sortedList)
            }


        }
        val isSorted=unsortedList.sorted()==controller.sortedList
        assertTrue(isSorted)
    }


}