package bubble_sort.ui.visulizer.contract


/**
 * Using this to reduce the responsible of client to manually calculating the value based on index
 * Though it seems reduce but it is helpful
 */
internal data class SwappedElement<T>(
    val j:Int,
    val jPlus1:Int,
    val jValue:T,
    val jPlus1Value:T
)
internal data class AlgoState<T>(
    val i: Int?,
    val j: Int?,
    val swapAblePair: SwappedElement<T> ?,
    val ended: Boolean,
    val shouldSwap: Boolean?,
    ){
     fun  toVariablesState(): List<AlgoVariablesState> {
        return listOf(
            AlgoVariablesState(
                name = "i",
                value = if (this.i != null) "${this.i}" else null
            ),
            AlgoVariablesState(
                name = "j",
                value = if (this.j != null) "${this.j}" else null
            ),
            AlgoVariablesState(
                name = "j+1",
                value = if (this.j != null) "${this.j+1}" else null
            ),

        )
    }

}


