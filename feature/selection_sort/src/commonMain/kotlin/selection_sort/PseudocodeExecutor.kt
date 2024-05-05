package selection_sort

import kotlinx.coroutines.flow.Flow

internal interface PseudocodeExecutor {
    val pseudocode: Flow<List<LineForPseudocode>>
    fun highLightLine(lineNumber:Int)
}