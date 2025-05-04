package quick_sort.data

sealed  interface QuickSortState{
    data object  Finish: QuickSortState
    data class State(
        val low: Int=0,
        val high: Int,
        val pivot: Int=high,
        val swapped: Pair<Int, Int>?=null,
        val i: Int?=-1,
        val j: Int?=0
    ): QuickSortState
}



interface  QuickSortIterator{
    companion object{
        fun create(list: List<Int>): QuickSortIterator = QuickSortIteratorImpl(list)
    }
    fun next(): QuickSortState
}
private class QuickSortIteratorImpl(
    list: List<Int>
): QuickSortIterator {
    private val states= mutableListOf<QuickSortState>()
    private var count=0
    override fun next(): QuickSortState {
        return try {
            states[count++]
        } catch (_:Exception){
            QuickSortState.Finish
        }
    }


    init {
        quickSort(list.toMutableList())
    }
    private fun quickSort(arr: MutableList<Int>, low: Int=0, high: Int=arr.lastIndex) {


        if (low < high) {
            // Partition the array and get the pivot index
            val pivotIndex = partition(arr, low, high)

            // Recursively sort left side
            quickSort(arr, low, pivotIndex - 1)
            // Recursively sort right side
            quickSort(arr, pivotIndex + 1, high)
        }
        else{
            states.add(
                QuickSortState.State(
                    low = low,
                    high = high,
                    j = null,
                    i = null,
                    swapped = null
                )
            )
        }
    }

    private fun partition(arr: MutableList<Int>, low: Int, high: Int): Int {

        val pivot = arr[high]
        var i = low - 1

        states.add(QuickSortState.State(i = i, low = low, high = high))

        // Process each element in [p..r-1]
        for (j in low until high) {
            states.add(QuickSortState.State(i = i, low = low, high = high, j = j))
            // If current element is <= pivot, move 'i' and quick_sort.ui.swap
            if (arr[j] <= pivot) {
                i++

                arr.swap(i, j)
                states.add(
                    QuickSortState.State(
                        i = i,
                        low = low,
                        high = high,
                        j = j,
                        swapped = Pair(i, j)
                    )
                )
            }
        }
        // Final quick_sort.ui.swap to put pivot in the correct location
        arr.swap(i + 1, high)
        states.add(
            QuickSortState.State(
                i = i,
                low = low,
                high = high,
                j = null,
                swapped = Pair(i + 1, high)
            )
        )

        return i + 1
    }

    // Swap extension function
    private fun MutableList<Int>.swap(i: Int, j: Int) {
        if (i == j) return
        val temp = this[i]
        this[i] = this[j]
        this[j] = temp
    }


}

