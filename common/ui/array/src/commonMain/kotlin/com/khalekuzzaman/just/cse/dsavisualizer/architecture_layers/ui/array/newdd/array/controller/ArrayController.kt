package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.components.ArrayCell
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.components.ArrayElement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


data class ArrayController<T>(
    private val list: List<T>,
    private val cellSizePx: Float,
) {
    val cells = MutableStateFlow(List(list.size) { index -> ArrayCell(elementId = index) })
   internal val elements = MutableStateFlow(list.mapIndexed { _, value -> ArrayElement(value = value) })

    val cellsCurrentElements: List<T?>
        get() = cells.value.map { if (it.elementId == null) null else elements.value[it.elementId].value }



    fun getCellPosition(index: Int?):Offset?{
        val arraySize = cells.value.size
        if (index==null) return null
        val isValid= index in 0..arraySize
        return if (isValid) cells.value[index].position else null
    }
    /**
     * Only needed while placing the cell,it denote the cell position,not the element position
     * It is needed when placing the cell.
     * if we need to move the element then use the [changeElementPosition]
     */

   internal fun onCellPositionChanged(index: Int, position: Offset) {
        runIfValid(index) {
            changeCellPosition(index, position)
            changeElementPosition(index, position)
        }

    }

    internal fun onDragElement(index: Int, dragAmount: Offset) {
        runIfValid(index) {
            val position = elements.value[index].position + dragAmount
            changeElementPosition(index, position)
        }


    }

    internal fun onDragEnd(elementIndex: Int) {
        runIfValid(elementIndex) {
            val snapAt = SnapUtils(cells.value.map { it.position }, cellSizePx)
                .findNearestCellId(elements.value[elementIndex].position)
            changeElementPosition(elementIndex, snapAt.second)
            val cellNo = snapAt.first
            val elementHasInsertedToACell = cellNo != -1
            if (elementHasInsertedToACell) {
                changeCellElementRef(cellNo, elementIndex)
            }
        }

    }

    internal fun onDragStart(indexOfElement: Int) {
        runIfValid(indexOfElement) {
            //find in which cells the element was removed
            val draggedFrom = cells.value.map { it.elementId }.indexOf(indexOfElement)
            val elementWasRemovedFromACell = draggedFrom != -1
            if (elementWasRemovedFromACell) {
                changeCellElementRef(draggedFrom, null)
            }
        }

    }

    private fun changeCellElementRef(index: Int, ref: Int?) {
        runIfValid(index) {
            cells.update { cell ->
                cell.mapIndexed { i, element ->
                    if (index == i)
                        element.copy(elementId = ref)
                    else element
                }

            }
        }
    }
    fun swapCellElement(i: Int, j: Int) {
        val arraySize = cells.value.size
        val isValidRange = (i in 0..<arraySize) && (j in 0..<arraySize)
        if (isValidRange) {
          //implement later
        }
    }
    /**
     * This useful for while dragging element,where the [position] can be any,not mandatory that
     * it is a cell position,so do not use it for swapping(without drag) because in case of swapping
     * we have to change the element reference also
     */
     fun changeElementPosition(index: Int, position: Offset) {
        runIfValid(index) {
            elements.update { elements ->
                elements.mapIndexed { i, element ->
                    if (index == i)
                        element.copy(position = position)
                    else element
                }

            }
        }
    }

    fun changeElementColor(index: Int, color: Color) {
        runIfValid(index) {
            elements.update { elements ->
                elements.mapIndexed { i, element ->
                    if (index == i)
                        element.copy(color = color)
                    else element
                }

            }
        }
    }

    private fun changeCellPosition(index: Int, position: Offset) {
        runIfValid(index) {
            cells.update { cells ->
                cells.mapIndexed { i, cell ->
                    if (index == i)
                        cell.copy(position = position)
                    else cell
                }

            }
        }

    }

    fun changeCellColor(index: Int?, color: Color) {
        //checking null and range here to avoid all client to check the valid index
        //if we have n client then we reduce the n times extra code
        runIfValid(index) {
            cells.update { cells ->
                cells.mapIndexed { i, cell ->
                    if (index == i)
                        cell.copy(color = color)
                    else cell
                }

            }
        }

    }


    private fun runIfValid(index: Int?, block: () -> Unit) {
       val isValid= (index!=null)&&(index in 0..<cells.value.size)
        if (isValid) {
            block()
        }
    }


}
