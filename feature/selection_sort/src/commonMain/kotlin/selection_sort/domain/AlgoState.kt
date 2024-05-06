package selection_sort.domain

/**
 * Using this to reduce the responsible of client to manually calculating the value based on index
 * Though it seems reduce but it is helpful
 */
internal data class SwappedElement<T>(
    val i: Int,
    val minIndex: Int,
    val iValue: T,
    val minIndexValue: T
)

internal data class AlgoState<T>(
    val i: Int?,
    val minIndex: Int?,
    val swappablePair: SwappedElement<T>?,
)


