package core.ui.array.swappable

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * Represent the cell state of an array that swappable but not draggable
 */
data class SwappableArrayCell<T>(
    val position: Offset = Offset.Zero,
    val element: SwappableElement<T>? = null,
    val color: Color = Color.Unspecified,
)

/**
 * represent the element of the array
 * @param value toString() of value will be used as label
 */
data class SwappableElement<T>(
    val elementId: String? = null,
    val position: Offset = Offset.Zero,
    val color: Color = Color.Unspecified,
    val value: T,
)

data class SwappableArrayController<T>(
    private val list: List<T>,
) {
    //Making element before place to cell

    //initialing the cell with initial elements
    val cells = MutableStateFlow(
        List(list.size) { index ->
            val element = SwappableElement(elementId = index.toString(), value = list[index])
            SwappableArrayCell(element = element)
        }
    )

    internal val cellCurrentElements: List<T?>
        get() = cells.value.map { cell ->
            if (cell.element == null) null
            else cell.element.value
        }


    fun getCellPosition(index: Int?): Offset? {
        val arraySize = cells.value.size
        if (index == null) return null
        val isValid = index in 0..arraySize
        return if (isValid) cells.value[index].position else null
    }

    /**
     * Only needed while placing the cell,it denote the cell position,not the element position
     * It is needed when placing the cell.
     * if we need to move the element then use the [swapElement]
     */
    //when the cell got the position from it layout

    internal fun onCellPositioned(index: Int, position: Offset) {
        //both cell and element position need to update onLayout
        if (isValidRange(index)) {
            cells.update { cells ->
                cells.mapIndexed { i, cell ->
                    if (index == i) {
                        val element = cell.element?.copy(position = position)
                        cell.copy(position = position, element = element)
                    } else cell
                }
            }
        }
    }


    /**
     * @param i is the cell index
     * @param j is the cell index
     */
    fun swapElement(i: Int, j: Int) {
        if (isValidRange(i) && isValidRange(j)) {
            //the will not position will not changed,the element position need to change
            val iPosition = cells.value[i].position
            val jPosition = cells.value[j].position
            var iElement = cells.value[i].element
            if (iElement != null)
                iElement = iElement.copy(position = jPosition)
            var jElement = cells.value[j].element
            if (jElement != null)
                jElement = jElement.copy(position = iPosition)
            cells.update { cells ->
                cells.mapIndexed { index, cell ->
                    when (index) {
                        i -> cell.copy(element = jElement)
                        j -> cell.copy(element = iElement)
                        else -> cell
                    }
                }
            }
        }
    }

    /**
     * [from] and [to] represent the index
     * * Note that the old value will be override and the [from] cell will be empty
     * @param isCopy is true then the remove will not removed from old cell
     * othewise it will removed form old cell
     */
    fun moveElement(from: Int, to: Int) {
        if (isValidRange(from) && isValidRange(to)) {
            //the will not position will not changed,the element position need to change
            val targetPosition = cells.value[to].position
            var element = cells.value[from].element
            if (element != null)
                element = element.copy(position = targetPosition)
            cells.update { cells ->
                cells.mapIndexed { index, cell ->
                    when (index) {
                        from -> cell.copy(element=null)//removing old element
                        to -> cell.copy(element = element)
                        else -> cell
                    }
                    }

            }
        }
    }
    fun copyElement(from: Int, to: Int) {
        if (isValidRange(from) && isValidRange(to)) {
            //the will not position will not changed,the element position need to change
            val targetPosition = cells.value[to].position
            var element = cells.value[from].element
            if (element != null)
                element = element.copy(position = targetPosition)
            cells.update { cells ->
                cells.mapIndexed { index, cell ->
                    if (index==to) cell.copy(element = element)
                    else  cell
                }
            }
        }
    }
    fun changeElementValue(targetIndex: Int, value:T) {
        if (isValidRange(targetIndex)) {
            cells.update { cells ->
                cells.mapIndexed { index, cell ->
                    if (targetIndex==index) {
                        val element=cell.element
                        if (element!=null){
                            cell.copy(element=element.copy(value = value))
                        }
                        else{
                           cell.copy(element= SwappableElement(value=value, position =cell.position ))
                        }
                    }
                    else  cell
                }
            }
        }
    }

    fun changeElementColor(index: Int, color: Color) {
        if (isValidRange(index)) {
            cells.update { cells ->
                cells.mapIndexed { i, cell ->
                    if (index == i) {
                        val element = cell.element
                        if (element != null)
                            cell.copy(element = element.copy(color = color))
                        else cell
                    } else cell
                }

            }
        }
    }


    fun changeCellColor(index: Int?, color: Color) {
        //checking null and range here to avoid all client to check the valid index
        //if we have n client then we reduce the n times extra code
        if (isValidRange(index)) {
            cells.update { cells ->
                cells.mapIndexed { i, cell ->
                    if (index == i)
                        cell.copy(color = color)
                    else cell
                }

            }
        }

    }

    private fun isValidRange(index: Int?) =
        (index != null) && (index >= 0 && index < cells.value.size)


}
