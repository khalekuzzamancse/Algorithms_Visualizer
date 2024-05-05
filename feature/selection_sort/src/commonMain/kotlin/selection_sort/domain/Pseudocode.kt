package selection_sort.domain

import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * - Used as separate from other module pseudocode so  that module can easily detach from other module
 * - Instead of Raw string,using Annotated String so that we can highlight it
 *
 */

interface Pseudocode {
    /**
     * @param indentationLevel the number of space from the left side
     * @param variableState is the variable value ,at this moment
     * @param line as [AnnotatedString] so that we can highlight it if needed
     */
    data class Line(
        val line: AnnotatedString,
        val highLighting: Boolean = false,
        val lineNumber: Int,
        val indentationLevel: Int = 0,
        val topPaddingLevel: Int = 0,
        val variableState: AnnotatedString? = null
    ) : Pseudocode

}

/**
 * Need to maintain the single source of truth,so that previous change is stored
 * Do not make it singleton so otherwise the reset or for re use it will preserve the old state,which leads
 * unexpected behaviour
 */

class AlgoPseudocode {

    private val code: List<Pseudocode.Line> = listOf(
        Pseudocode.Line(
            line = AnnotatedString("selectionSort( list ) {"),
            lineNumber = 1,
            indentationLevel = 0,
        ),
        Pseudocode.Line(
            line = AnnotatedString("len = list.size"),
            lineNumber = 2,
            indentationLevel = 1,
        ),
        Pseudocode.Line(
            line = AnnotatedString("for ( i = 0; i < len - 1; i++ ) {"),
            lineNumber = 3,
            indentationLevel = 1,
            topPaddingLevel = 1
        ),
        Pseudocode.Line(
            line = AnnotatedString("minIndex = findMinIndex(list, i, len)"),
            lineNumber = 4,
            indentationLevel = 2,
        ),
        Pseudocode.Line(
            line = AnnotatedString("isMinFound = ( minIndex != i )"),
            lineNumber = 5,
            indentationLevel = 2,
        ),
        Pseudocode.Line(
            line = AnnotatedString("if ( isMinFound )"),
            lineNumber = 6,
            indentationLevel = 2,
        ),
        Pseudocode.Line(
            line = AnnotatedString("swap( list[i], list[minIndex] )"),
            lineNumber = 7,
            indentationLevel = 3,
        ),
        Pseudocode.Line(
            line = AnnotatedString("}"),
            lineNumber = 8,
            indentationLevel = 1,
            topPaddingLevel = 1
        ),
        Pseudocode.Line(
            line = AnnotatedString("}"),
            lineNumber = 9,
            indentationLevel = 0,
        )
    )

    private val _codes = MutableStateFlow(code)
    val codes = _codes.asStateFlow()

    fun highlightPseudocode(lineNumber: Int) {
        _codes.update { codes ->
            codes.map {
                if (it.lineNumber == lineNumber)
                    it.copy(highLighting = true)
                else it.copy(highLighting = false)
            }
        }
    }

    fun <T : Comparable<T>> updateStates(
        len: Int? = null,
        i: Int? = null,
        minIndex: Int? = null,
        iValue: T? = null,
        minIndexValue: T? = null
    ) {
        if (len != null) updateLine2(len) else hideStateOfLine(2)
        if (i != null) updateLine3(i) else hideStateOfLine(3)
        if (minIndex != null) {
            updateLine4(minIndex) // list[minIndex]
            if (iValue != null && minIndexValue != null && iValue != minIndexValue) {
                updateLine5(iValue, minIndexValue) // isMinFound calculation
                updateLine6(iValue, minIndexValue) // if condition
                updateLine7(iValue, minIndexValue) // swap
            }
        } else {
            hideStateOfLine(4)
            hideStateOfLine(5)
            hideStateOfLine(6)
            hideStateOfLine(7)
        }
    }

    private fun updateLine2(len: Int) = updateLine(2, AnnotatedString("len : $len"))
    private fun updateLine3(i: Int) = updateLine(3, AnnotatedString("i : $i"))
    private fun updateLine4(minIndex: Int) = updateLine(4, AnnotatedString("minIndex : $minIndex"))
    private fun <T : Comparable<T>> updateLine5(iValue: T, minIndexValue: T) =
        updateLine(5, AnnotatedString("isMinFound : ${iValue != minIndexValue}"))
    private fun <T : Comparable<T>> updateLine6(iValue: T, minIndexValue: T) =
        updateLine(6, AnnotatedString("isMinFound"))
    private fun <T : Comparable<T>> updateLine7(iValue: T, minIndexValue: T) =
        updateLine(7, AnnotatedString("swap( $iValue , $minIndexValue )"))

    private fun updateLine(lineNumber: Int, string: AnnotatedString) {
        _codes.update { codes ->
            codes.map { code ->
                if (code.lineNumber == lineNumber) {
                    code.copy(variableState = string)
                } else code
            }
        }
    }

    private fun hideStateOfLine(lineNumber: Int) {
        _codes.update { codes ->
            codes.map { code ->
                if (code.lineNumber == lineNumber) {
                    code.copy(variableState = null)
                } else code
            }
        }
    }
}
