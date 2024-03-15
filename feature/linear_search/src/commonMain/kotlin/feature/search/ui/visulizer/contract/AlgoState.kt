package feature.search.ui.visulizer.contract


internal data class AlgoState<T>(
    val target: T,
    val currentIndex: Int?,
    val searchEnded: Boolean,
    val isMatched: Boolean? = null,
    val currentElement: T?,
){
     fun  toVariablesState(): List<AlgoVariablesState> {
        return listOf(
            AlgoVariablesState(
                name = "target",
                value = if (this.target != null) "${this.target}" else null
            ),
            AlgoVariablesState(
                name = "i",
                value = if (this.currentIndex != null) "${this.currentIndex}" else null
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


