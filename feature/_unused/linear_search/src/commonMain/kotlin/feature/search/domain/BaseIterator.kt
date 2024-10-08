package feature.search.domain

/**
 * It is used to reduce the boiler plat code of the the those iterator
 * that are going to use the Sequence builder.
 * * It give us loose coupled as a easily underlying different implementation can provide
 * without distributing the client
 */
abstract class BaseIterator<T : Comparable<T>>(
    numberOfElement:Int,
    private val target: T//target
) {
    abstract val result: Sequence<VisualizationState>
    protected var length = numberOfElement
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