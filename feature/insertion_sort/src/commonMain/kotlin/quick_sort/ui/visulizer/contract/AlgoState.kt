package quick_sort.ui.visulizer.contract


/**
 * Using this to reduce the responsible of client to manually calculating the value based on index
 * Though it seems reduce but it is helpful
 */
internal data class AlgoState<T>(
    val i: Int? = null,
    val j: Int? = null,
    val shiftedIndex:Int?,
    val keyFinalPosition:Int?,
    val key:T?=null,
) {
    fun toVariablesState(): List<AlgoVariablesState> {
        return listOf()
    }

}


