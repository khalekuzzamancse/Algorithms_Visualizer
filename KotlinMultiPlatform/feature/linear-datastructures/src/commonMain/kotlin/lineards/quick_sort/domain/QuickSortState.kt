package lineards.quick_sort.domain

sealed  interface QuickSortState{
    data object  Finish: QuickSortState
    data class State(val low: Int=0, val high: Int, val pivot: Int=high,
        val swapped: Pair<Int, Int>?=null, val i: Int?=-1, val j: Int?=0): QuickSortState
}
