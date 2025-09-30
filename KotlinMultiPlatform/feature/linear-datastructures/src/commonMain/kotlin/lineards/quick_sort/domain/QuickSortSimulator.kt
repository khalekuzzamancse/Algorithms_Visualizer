package lineards.quick_sort.domain

import lineards.quick_sort.data.QuickSortSimulatorImpl

interface  QuickSortSimulator{
    fun next(): QuickSortState
    companion object{
        fun create(list: List<Int>): QuickSortSimulator = QuickSortSimulatorImpl(list)
    }
}