package feature.search.domain

abstract class BaseIterator<T : Comparable<T>>(
    val elements: List<T>,
     val target: T//target
) {
    abstract val result: Sequence<VisualizationState>
    protected var length = elements.size
    protected var index = 0 // Before search starts
    protected var searchEnded = false
    protected  var isFound: Boolean? = null
    protected  var current: T? = null
    private val algoPseudocode = AlgoPseudocode()
    open val pseudocode=algoPseudocode.codes
    protected fun createEndState(): VisualizationState.Finished {
        val isFound = index != -1
        return VisualizationState.Finished(
            foundedIndex = index,
            comparisons = if (isFound) index + 1 else length
        )
    }

    protected fun newState(): VisualizationState.AlgoState<T> {
        return VisualizationState.AlgoState(
            target = target,
            currentIndex = index,
            currentElement = current
        )
    }

    protected fun updateVariablesState(
        length: Int? = null,
        target: T? = null,
        index: Int? = null,
        current: T? = null
    ) {
        algoPseudocode.updateStates(
            length = length,
            target = target,
            index = index,
            current = current
        )
    }

    protected fun updatePseudocode(lineNo: Int) {
        algoPseudocode.highLightPseudocode(lineNo)

    }
}