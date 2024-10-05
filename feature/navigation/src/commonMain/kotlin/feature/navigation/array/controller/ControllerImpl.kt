@file:Suppress("functionName", "unused","LocalVariableName")

package feature.navigation.array.controller

import androidx.compose.ui.geometry.Offset
import feature.navigation.array.model.Cell
import feature.navigation.array.model.Element
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.abs

class ControllerImpl(
    private val list: List<String> // list of labels

) {
    private val _cells = MutableStateFlow(List(list.size) { index -> _createEmptyCell(index) })
    private val _elements =
        MutableStateFlow(List(list.size) { index -> _createEmptyElement(index, list[index]) })

    private val _pointerPosition = MutableStateFlow<Offset?>(null)
    val pointerPosition = _pointerPosition.asStateFlow()

    fun movePointer(index: Int){
        _pointerPosition.update { cells.value[index].position }
    }


    private val _allCellPlaced = MutableStateFlow(false)
    val allCellPlaced = _allCellPlaced.asStateFlow()

    val cells = _cells.asStateFlow()
    val elements = _elements.asStateFlow()


    private fun _createEmptyCell(index: Int) = Cell(
        index = index,
        position = Offset.Zero
    )

    val numberOfElements: Int
        get() = list.size

    private fun _createEmptyElement(index: Int, label: String) = Element(
        id = index,
        position = Offset.Zero,
        label = label
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


    fun onCellPositionChanged(index: Int, position: Offset) {
        changeCellPosition(index, position)
        // changeElementPosition(index, position) TODO:Side effect can cause unwanted behaviour

        _allCellPlaced.update {
            val positions = (cells.value.map { it.position })
            val noTwoElementHasSamePosition = (positions.size == positions.toSet().size)
            noTwoElementHasSamePosition
        }

    }


    fun swap(i: Int, j: Int) {
        val positionOf_i = elements.value[i].position
        val positionOf_j = elements.value[j].position
        changeElementPosition(i, positionOf_j)
        changeElementPosition(j, positionOf_i)
    }



    fun changeElementPosition(index: Int, position: Offset) {
        _elements.update { elements ->
            elements.mapIndexed { i, element ->
                if (index == i)
                    element.copy(position = position)
                else element
            }

        }
    }

    private fun changeCellPosition(index: Int, position: Offset) {
        runIfValid(index) {
            _cells.update { cells ->
                cells.mapIndexed { i, cell ->
                    if (index == i)
                        cell.copy(position = position)
                    else cell
                }

            }
        }

    }

    private fun runIfValid(index: Int, block: () -> Unit) {
        val isValid = index in list.indices
        if (isValid) {
            block()
        }
    }

}