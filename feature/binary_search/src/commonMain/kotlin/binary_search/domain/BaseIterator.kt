package binary_search.domain

/**
 * It is used to reduce the boiler plat code of the the those iterator
 * that are going to use the Sequence builder.
 * * It give us loose coupled as a easily underlying different implementation can provide
 * without distributing the client
 */
abstract class BaseIterator<T : Comparable<T>>(
    numberOfElement: Int,
    private val target: T//target
) {
    abstract val result: Sequence<VisualizationState>
    protected var length = numberOfElement
    protected var midValue: T? = null
    protected var low = 0
    protected var mid:Int? = null
    protected var high = length - 1

    private val algoPseudocode = AlgoPseudocode()
    open val pseudocode = algoPseudocode.codes
    protected fun createEndState(): VisualizationState.Finished {
        return VisualizationState.Finished(
            low = low,
            high = high,
            mid=mid
        )
    }

    protected fun newState(mid: Int? = null): VisualizationState.AlgoState<T> {
        return VisualizationState.AlgoState(
            target = target,
            low = low,
            high = high,
            mid = mid,
            midValue = if (mid == null) null else midValue,
        )
    }
    fun initialState(): VisualizationState.AlgoState<T>{////uses as public for that
        return VisualizationState.AlgoState(
            target = target,
            low = null,
            high =null,
            mid = null,
            midValue = null,
        )
    }

    protected fun updateVariablesState(
        length: Int? = null,
        target: T? = null,
        low: Int? = null,
        high: Int? = null,
        mid: Int? = null,
        midValue: T? = null
    ) {
        algoPseudocode.updateStates(
            length = length,
            target = target,
            low = low,
            high = high,
            mid = mid,
            midValue = midValue,
        )
    }

    protected fun updatePseudocode(lineNo: Int) {
        algoPseudocode.highLightPseudocode(lineNo)

    }
}