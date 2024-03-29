package bubble_sort.infrastructure

import kotlin.test.Test
import kotlin.test.assertTrue

class BubbleSortIteratorTest {
    @Test
    fun `test bubble sort iterator`() {
        // Arrange
        val unsortedList = listOf(4, 2, 5, 1, 3)
        val iterator = BubbleSortIterator(unsortedList)
        iterator.result.iterator().forEach {
            println(iterator.sortedList)
        }
        // Act
        val isSorted = unsortedList.sorted()==iterator.sortedList
        assertTrue(isSorted)
    }
    @Test
    fun `testing for sorted array`() {
        // Arrange
        val unsortedList = listOf(4,3,2,1,7,6,8,5)
        val iterator = BubbleSortIterator(unsortedList)
        iterator.result.iterator().forEach {
            println(iterator.sortedList)
        }
        // Act
        val isSorted = unsortedList.sorted()==iterator.sortedList
        assertTrue(isSorted)
    }


}
