@file:Suppress("functionName", "unused", "LocalVariableName")

package web.x.core.array.controller

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import web.x.core.array.model.Cell
import web.x.core.array.model.Element
import web.x.core.array.model.Pointer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import web.x.core.ArrayColor

class VisualArrayControllerImpl internal constructor(
    private val itemLabels: List<String>,
    pointersLabel: List<String> = emptyList(),
) : VisualArrayController() {

    private val _cells =
        MutableStateFlow(List(itemLabels.size) { index -> _createEmptyCell(index) })
    private val _elements =
        MutableStateFlow(List(itemLabels.size) { index ->
            _createEmptyElement(
                index,
                itemLabels[index]
            )
        })

    private val _pointerPosition = MutableStateFlow<Offset?>(null)
    override val pointerPosition = _pointerPosition.asStateFlow()

    private val _pointers = MutableStateFlow(pointersLabel.map { label -> Pointer(label = label) })
    override val pointers: StateFlow<List<Pointer>> = _pointers.asStateFlow()

    override fun movePointer(label: String, index: Int) {
        _try {
            _pointers.update { pointers ->
                pointers.map { pointer ->
                    if (pointer.label == label)
                        pointer.copy(position = cells.value[index].position)
                    else pointer

                }

            }
        }

    }


    private val _allCellPlaced = MutableStateFlow(false)
    override val allCellPlaced = _allCellPlaced.asStateFlow()

    override val cells = _cells.asStateFlow()
    override val elements = _elements.asStateFlow()


    private fun _createEmptyCell(index: Int) = Cell(
        index = index,
        position = Offset.Zero,
        color = ArrayColor.CELL_COLOR
    )

    override val numberOfElements: Int
        get() = itemLabels.size

    private fun _createEmptyElement(index: Int, label: String) = Element(
        position = Offset.Zero,
        label = label,
        color = ArrayColor.ELEMENT
    )

    init {
        CoroutineScope(Dispatchers.Default).launch {
            allCellPlaced.collect { placed ->
                if (placed) {
                    cells.value.forEachIndexed { index, cell ->
                        changeElementPosition(index, cell.position)
                    }
                }

            }

        }
    }


    override fun onCellPositionChanged(index: Int, position: Offset) {
        changeCellPosition(index, position)
        // changeElementPosition(index, position) TODO:Side effect can cause unwanted behaviour

        _allCellPlaced.update {
            val positions = (cells.value.map { it.position })
            val noTwoElementHasSamePosition = (positions.size == positions.toSet().size)
            noTwoElementHasSamePosition
        }

    }

    override suspend fun swap(i: Int, j: Int, delay: Long) {
        _trySuspend {
            val iTh = elements.value[i]
            val jTh = elements.value[j]
            //Need to change the position for visual nice animations
            //Then after a while need to update the original list
            //But we we do just update the list element without changing the position first then we don't get nice swapping animation
            changeElementPosition(i, jTh.position)
            changeElementPosition(j, iTh.position)
            changeElementColor(i, jTh.color)
            changeElementColor(j, iTh.color)


            delay(delay)//Mandatory for nice moving animations

            val elements = elements.value.toList().toMutableList()//making copy for safety
            val temp = elements[i]
            elements[i] = _elements.value[j]
            elements[j] = temp

            _elements.update { elements }
        }


    }


    override fun changeElementPosition(index: Int, position: Offset) {
        _try {
            _elements.update { elements ->
                elements.mapIndexed { i, element ->
                    if (index == i)
                        element.copy(position = position)
                    else element
                }

            }
        }
    }

    private fun changeCellPosition(index: Int, position: Offset) {
        _try {
            _cells.update { cells ->
                cells.mapIndexed { i, cell ->
                    if (index == i)
                        cell.copy(position = position)
                    else cell
                }

            }
        }


    }

    override fun changeCellColorUpTo(index: Int, color: Color) {
        _try {
            _cells.update { cells ->
                cells.mapIndexed { ind, cell ->
                    if (ind <= index)
                        cell.copy(color = color)
                    else cell

                }

            }
        }

    }

    override fun changeCellColor(index: Int, color: Color) {
        _try {
            _cells.update { cells ->
                cells.mapIndexed { ind, cell ->
                    if (ind == index)
                        cell.copy(color = color)
                    else cell

                }

            }
        }
    }

    override fun changeElementColor(index: Int, color: Color) {
        _elements.update { elements ->
            elements.mapIndexed { i, element ->
                if (index == i)
                    element.copy(color = color)
                else element
            }

        }
    }

    override fun removePointers(labels: List<String>) {
        _pointers.update { pointers ->
            pointers.filter { pointer -> !(labels.contains(pointer.label)) }
        }
    }

    override fun hidePointer(label: String) {
        _pointers.update { pointers ->
            pointers.map { pointer ->
                if (label == pointer.label)
                    pointer.copy(position = null)
                else pointer
            }

        }
    }


    private fun _try(block: () -> Unit) {
        try {
            block()
        } catch (_: Throwable) {

        }
    }

    private suspend fun _trySuspend(block: suspend () -> Unit) {
        try {
            block()
        } catch (_: Throwable) {

        }
    }

}