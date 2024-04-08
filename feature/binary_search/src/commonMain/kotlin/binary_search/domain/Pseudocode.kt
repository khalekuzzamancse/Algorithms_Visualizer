package binary_search.domain

import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Used as separate from other module pseudocode so that
 * that module can easily detach from other module
 *
 */

interface Pseudocode {
    /**
     * @param indentationLevel the number of space from the left side
     * @param variableState is the variable value ,at this moment
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
            line = AnnotatedString("binarySearch(list, target) {"),
            lineNumber = 1,
            indentationLevel = 0,
        ),
        Pseudocode.Line(
            line = AnnotatedString("len = list.size"),
            lineNumber = 2,
            indentationLevel = 1,
        ),
        Pseudocode.Line(
            line = AnnotatedString("low = 0"),
            lineNumber = 3,
            indentationLevel = 1,
        ),
        Pseudocode.Line(
            line = AnnotatedString("high = len - 1"),
            lineNumber = 4,
            indentationLevel = 1,
        ),
        Pseudocode.Line(
            line = AnnotatedString("while (low <= high) {"),
            lineNumber = 5,
            indentationLevel = 1,
        ),
        Pseudocode.Line(
            line = AnnotatedString("mid = (low + high) / 2"),
            lineNumber = 6,
            indentationLevel = 2,
        ),
        Pseudocode.Line(
            line = AnnotatedString("current = list[mid]"),
            lineNumber = 7,
            indentationLevel = 2,
        ),

        Pseudocode.Line(
            line = AnnotatedString("if (current == target) {"),
            lineNumber = 8,
            indentationLevel = 2,
        ),
        Pseudocode.Line(
            line = AnnotatedString("    return mid"),
            lineNumber = 10,
            indentationLevel = 3,
        ),
        Pseudocode.Line(
            line = AnnotatedString(" else if (current < target)"),
            lineNumber = 13,
            indentationLevel = 1,
            topPaddingLevel = 1
        ),

        Pseudocode.Line(
            line = AnnotatedString("    low = mid + 1"),
            lineNumber = 14,
            indentationLevel = 1,
        ),
        Pseudocode.Line(
            line = AnnotatedString("  else"),
            lineNumber = 13,
            indentationLevel = 1,
            topPaddingLevel = 1
        ),
        Pseudocode.Line(
            line = AnnotatedString("       high = mid - 1"),
            lineNumber = 15,
            indentationLevel = 0,
        ),
        Pseudocode.Line(
            line = AnnotatedString("  }"),
            lineNumber = 15,
            indentationLevel = 0,
        ),
        Pseudocode.Line(
            line = AnnotatedString(" return -1"),
            lineNumber = 15,
            indentationLevel = 0,
        ),
        Pseudocode.Line(
            line = AnnotatedString("}"),
            lineNumber = 15,
            indentationLevel = 0,
        ),
    )


    private val _codes = MutableStateFlow(code)
    val codes = _codes.asStateFlow()


    fun highLightPseudocode(lineNumber: Int) {
        _codes.update { codes ->
            codes.map {
                if (it.lineNumber == lineNumber)
                    it.copy(highLighting = true)
                else it.copy(highLighting = false) //removing old highlight as false

            }
        }
    }

    fun <T : Comparable<T>> updateStates(
        length: Int? = null,
        target: T? = null,
        low: Int? = null,
        high: Int? = null,
        mid: Int? = null,
        midValue: T? = null
    ) {

        if (target != null) updateLine1(target) else hideStateOfLine(1)
        if (length != null) updateLine2(length) else hideStateOfLine(2)
        if (low != null) updateLine3(low) else hideStateOfLine(3)
        if (high != null) updateLine4(high) else hideStateOfLine(4)
        if (low != null && high != null) updateLine5(low, high) else hideStateOfLine(5)
        if (mid != null) updateLine6(mid) else hideStateOfLine(6)
        if (midValue != null) updateLine7(midValue) else hideStateOfLine(7)
        if (midValue != null&&target!=null) updateLine8(midValue,target) else hideStateOfLine(8)

    }

    private fun <T> updateLine1(target: T) = updateLine(1, AnnotatedString(" target : $target"))
    private fun updateLine2(length: Int) = updateLine(2, AnnotatedString(" len : $length"))
    private fun updateLine3(low: Int) = updateLine(3, AnnotatedString(" low : $low"))
    private fun updateLine4(high: Int) = updateLine(4, AnnotatedString(" high : $high"))
    private fun updateLine5(low: Int, high: Int) = updateLine(5, AnnotatedString("$low <= $high: ${low <= high}"))
    private fun updateLine6(mid: Int) = updateLine(6, AnnotatedString("mid : $mid"))
    private fun <T> updateLine7(midValue: T) = updateLine(7, AnnotatedString("current : $midValue"))
    private fun <T> updateLine8(midValue: T,target:T) = updateLine(8, AnnotatedString("$midValue == $target : ${midValue==target}"))
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

