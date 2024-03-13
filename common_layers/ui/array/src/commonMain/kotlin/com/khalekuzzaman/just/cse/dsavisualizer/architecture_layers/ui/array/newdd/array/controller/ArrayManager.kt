package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.cell.ArrayCell
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


 data class ArrayManager<T>(
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
        val position = elements.value[index].position + dragAmount
        changeElementPosition(index, position)
    }

    fun onDragEnd(elementIndex: Int) {
        val snapAt = SnapUtils(cells.value.map { it.position }, cellSizePx)
            .findNearestCellId(elements.value[elementIndex].position)
        changeElementPosition(elementIndex, snapAt.second)
        val cellNo = snapAt.first
        val elementHasInsertedToACell = cellNo != -1
        if (elementHasInsertedToACell) {
            changeCellElementRef(cellNo, elementIndex)
        }
    }

    fun onDragStart(indexOfElement: Int) {
        //find in which cells the element was removed
        val draggedFrom = cells.value.map { it.elementRef }.indexOf(indexOfElement)
        val elementWasRemovedFromACell = draggedFrom != -1
        if (elementWasRemovedFromACell) {
            changeCellElementRef(draggedFrom, null)
        }
    }

    private fun changeCellElementRef(index: Int, ref: Int?) {
        cells.update { cell ->
            cell.mapIndexed { i, element ->
                if (index == i)
                    element.copy(elementRef = ref)
                else element
            }

        }
    }

    private fun changeElementPosition(index: Int, position: Offset) {
        elements.update { elements ->
            elements.mapIndexed { i, element ->
                if (index == i)
                    element.copy(position = position)
                else element
            }

        }
    }
     fun changeElementColor(index: Int, color: Color) {
        elements.update { elements ->
            elements.mapIndexed { i, element ->
                if (index == i)
                    element.copy(color=color)
                else element
            }

        }
    }
    private fun changeCellPosition(index: Int, position: Offset) {
        cells.update { cells ->
            cells.mapIndexed { i, cell ->
                if (index == i)
                    cell.copy(position = position)
                else cell
            }

        }
    }
     fun changeCellColor(index: Int,color: Color) {
        cells.update { cells ->
            cells.mapIndexed { i, cell ->
                if (index == i)
                    cell.copy(color = color)
                else cell
            }

        }
    }


}
