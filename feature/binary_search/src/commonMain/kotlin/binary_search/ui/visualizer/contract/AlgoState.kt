package binary_search.ui.visualizer.contract


internal data class AlgoState<T>(
    val target: T,
    val low: Int?,
    val high:Int?,
    val mid:Int?,
    val currentElement: T?,
    val searchEnded: Boolean,
    val isMatched: Boolean? = null,

){
     fun  toVariablesState(): List<AlgoVariablesState> {
        return listOf(
            AlgoVariablesState(
                name = "target",
                value = if (this.target != null) "${this.target}" else null
            ),
            AlgoVariablesState(
                name = "low",
                value = if (this.low != null) "${this.low}" else null
            ),
            AlgoVariablesState(
                name = "high",
                value = if (this.high != null) "${this.high}" else null
            ),
            AlgoVariablesState(
                name = "mid",
                value = if (this.mid != null) "${this.mid}" else null
            ),
            AlgoVariablesState(
                name = "current",
                value = if (this.currentElement != null) "${this.currentElement}" else null
            ),
            AlgoVariablesState(
                name = "isMatched",
                value = if (this.isMatched != null) "${this.isMatched}" else null
            ),
            AlgoVariablesState(
                name = "isEnded",
                value = "${this.searchEnded}"
            ),
        )
    }

}


