package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.controller

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


/**
 * @param value toString() of value will be used as label
 */
internal data class ArrayElement<T>(
    val position: Offset = Offset.Zero,
    val value: T,
)

internal data class ArrayCell(
    val position: Offset = Offset.Zero,
    val elementRef: Int? = null
)

internal data class ArrayManager<T>(
    private val list: List<T>,
    private val cellSizePx: Float,
) {
    val cells = MutableStateFlow(List(list.size) { index -> ArrayCell(elementRef = index) })
    val elements = MutableStateFlow(list.mapIndexed { _, value -> ArrayElement(value = value) })

    val cellsCurrentElements: List<T?>
        get() = cells.value.map { if (it.elementRef == null) null else elements.value[it.elementRef].value }

    fun onCellPositionChanged(index: Int, position: Offset) {
        changeCellPosition(index, position)
        changeElementPosition(index, position)
    }

    fun onDragElement(index: Int, dragAmount: Offset) {
        val position=elements.value[index].position+dragAmount
        changeElementPosition(index, position)
    }

    fun onDragEnd(elementIndex: Int) {
        val snapAt = SnapUtils(cells.value.map { it.position }, cellSizePx)
            .findNearestCellId(elements.value[elementIndex].position)
        changeElementPosition(elementIndex, snapAt.second)
        val cellNo = snapAt.first
        val elementHasInsertedToACell=cellNo != -1
        if (elementHasInsertedToACell) {
            changeCellElementRef(cellNo,elementIndex)
        }
    }

    fun onDragStart(indexOfElement: Int) {
        //find in which cells the element was removed
        val draggedFrom = cells.value.map { it.elementRef }.indexOf(indexOfElement)
        val elementWasRemovedFromACell=draggedFrom != -1
        if (elementWasRemovedFromACell) {
            changeCellElementRef(draggedFrom,null)
        }
    }
    private fun changeCellElementRef(index: Int,ref:Int?){
        cells.update { cell ->
            cell.mapIndexed { i, element ->
                if (index == i)
                    element.copy(elementRef = ref)
                else element
            }

        }
    }
    private fun changeElementPosition(index: Int,position: Offset){
        elements.update { elements ->
            elements.mapIndexed { i, element ->
                if (index == i)
                    element.copy(position = position)
                else element
            }

        }
    }
    private fun changeCellPosition(index: Int,position: Offset){
        cells.update { cells ->
            cells.mapIndexed { i, cell ->
                if (index == i)
                    cell.copy(position = position)
                else cell
            }

        }
    }



}
