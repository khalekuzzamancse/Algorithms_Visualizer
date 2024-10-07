package bubble_sort.domain

import kotlinx.coroutines.flow.StateFlow

/**
 * It is used to reduce the boiler plat code of the the those iterator
 * that are going to use the Sequence builder.
 * * It give us loose coupled as a easily underlying different implementation can provide
 * without distributing the client
 */
internal abstract class BaseIterator<T : Comparable<T>>(
    list: List<T>,
) {
    abstract val result: Sequence<VisualizationState>
    val sortedList = list.toMutableList()
    protected var length = list.size
    protected var i = 0
    protected var j = 0
    protected var swappablePair: SwappedPair<T>? = null
    private val algoPseudocode = AlgoPseudocode()
    val pseudocode: StateFlow<List<Pseudocode.Line>> = algoPseudocode.codes
    protected fun createEndState(): VisualizationState.Finished {
        return VisualizationState.Finished
    }

    //explicitly update state to avoid side effect and unwanted behaviour
    protected fun newState(
        i: Int? = null,
        j: Int? = null, swappedPair: SwappedPair<T>? = null
    ): VisualizationState.AlgoState<T> {
        return VisualizationState.AlgoState(
            i = i, j = j, swappablePair = swappedPair
        )
    }

    protected fun updateVariablesState(
        len: Int? = null,
        i: Int? = null,
        j: Int? = null,
        jValue: T? = null,
        jPlus1Value: T? = null
    ) {
        algoPseudocode.updateStates(
            len = len,
            i = i,
            j = j,
            jValue = jValue,
            jPlus1Value = jPlus1Value

        )
    }

    private fun updatePseudocode(lineNo: Int) {
        algoPseudocode.highLightPseudocode(lineNo)

    }
}