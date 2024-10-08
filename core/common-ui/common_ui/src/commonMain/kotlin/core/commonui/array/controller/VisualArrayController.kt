package core.commonui.array.controller

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import core.commonui.array.model.Cell
import core.commonui.array.model.Element
import core.commonui.array.model.Pointer
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

    /**
     * Set the pointer position to null so that it not drawn,but it is not delete the pointer itself
     */
    abstract fun hidePointer(label: String)
    abstract fun changeElementColor(index: Int,color:Color)

    /**
     * delete the pointers permanently
     */
    abstract fun removePointers(labels:List<String>)

    /**
     * - Helpful for marking partitions such as sorted or unsorted in case of insertion sort
     */
    abstract fun changeCellColorUpTo(index: Int,color:Color)


}
