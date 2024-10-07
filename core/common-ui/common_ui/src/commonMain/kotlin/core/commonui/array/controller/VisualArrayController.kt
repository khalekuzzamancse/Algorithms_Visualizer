package core.commonui.array.controller

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import core.commonui.array.model.Cell
import core.commonui.array.model.Element
import core.commonui.array.model.Pointer
import kotlinx.coroutines.Delay
import kotlinx.coroutines.flow.StateFlow

/**
 * - Using abstract class instead if interface because need to make some function or property internal or protected
 */
abstract class VisualArrayController {
    internal abstract val pointerPosition: StateFlow<Offset?>
    internal abstract val allCellPlaced: StateFlow<Boolean>
    internal abstract val cells: StateFlow<List<Cell>>
    internal abstract val elements: StateFlow<List<Element>>
    internal abstract val numberOfElements: Int
    internal abstract fun onCellPositionChanged(index: Int, position: Offset)
    internal abstract fun changeElementPosition(index: Int, position: Offset)
    internal abstract val pointers: StateFlow<List<Pointer>>

    /**
     * - Delay is mandatory,disable the next swap until the delay finished,otherwise loose nice moving effect
     */
    abstract suspend fun swap(i: Int, j: Int,delay: Long)
    abstract fun movePointer(label: String,index: Int)
    abstract fun changeElementColor(index: Int,color:Color)
    abstract fun clearPointers(labels:List<String>)


}
