package feature.search.domain

import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
/**
 * Used as separate from other module pseudocode so that
 * that module can easily detach from other module
 *
 */

interface Pseudocode{
    /**
     * @param indentationLevel the number of space from the left side
     * @param variableState is the variable value ,at this moment
     */
    data class Line(
        val line: AnnotatedString,
        val highLighting: Boolean = false,
        val lineNumber: Int,
        val indentationLevel:Int=0,
        val topPaddingLevel:Int=0,
        val variableState:AnnotatedString?=null
    ): Pseudocode

}


/**
 * Need to maintain the single source of truth,so that previous change is stored
 * Do not make it singleton so otherwise the reset or for re use it will preserve the old state,which leads
 * unexpected behaviour
 */
class AlgoPseudocode {

    private val code: List<Pseudocode.Line> = listOf(
        Pseudocode.Line(
            line = AnnotatedString("LinearSearch( list, target ) {"),
            lineNumber = 1,
            indentationLevel = 0,
        ),
        Pseudocode.Line(
            line = AnnotatedString("len = list.size"),
            lineNumber = 3,
            indentationLevel = 1,
        ),
        Pseudocode.Line(
            line = AnnotatedString("for ( index=0 ; i<len; index++) {"),
            lineNumber = 5,
            indentationLevel = 1,
            topPaddingLevel = 1
        ),
        Pseudocode.Line(
            line = AnnotatedString("current = list[index]"),
            lineNumber = 6,
            indentationLevel = 2,
        ),
        Pseudocode.Line(
            line = AnnotatedString("isFound = (current==target)"),
            lineNumber = 7,
            indentationLevel = 2,
        ),
        Pseudocode.Line(
            line = AnnotatedString("if ( isFound )"),
            lineNumber = 8,
            indentationLevel = 2,
        ),
        Pseudocode.Line(
            line = AnnotatedString("return index"),
            lineNumber = 10,
            indentationLevel = 3,
        ),
        Pseudocode.Line(
            line = AnnotatedString("}"),
            lineNumber = 13,
            indentationLevel = 1,
            topPaddingLevel = 1
        ),

        Pseudocode.Line(
            line = AnnotatedString("return -1"),
            lineNumber = 14,
            indentationLevel = 1,
        ),
        Pseudocode.Line(
            line = AnnotatedString("}"),
            lineNumber = 15,
            indentationLevel = 0,
        )
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
        index: Int? = null,
        current: T? = null
    ) {
        //3: len=...
        if (target != null) showStateOfLine1(target) else hideStateOfLine(1)
        if (length != null) showStateOfLine3(length) else hideStateOfLine(3)
        if (index != null) updateLine5(index) else hideStateOfLine(5)
        if (current != null) updateLine6(current) else hideStateOfLine(6)
        if (current != null && target != null) updateLine7N8(current, target) else {
            hideStateOfLine(7);hideStateOfLine(8)
        }


    }


    private fun showStateOfLine3(length: Int) {
        _codes.update { codes ->
            codes.map { code ->
                if (code.lineNumber == 3) {
                    code.copy(variableState = AnnotatedString(" len : $length"))
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

    private fun <T> showStateOfLine1(target: T) {
        _codes.update { codes ->
            codes.map { code ->
                if (code.lineNumber == 1) {
                    code.copy(variableState = AnnotatedString(" target : $target"))
                } else code
            }
        }
    }

    private fun updateLine5(index: Int) {
        _codes.update { codes ->
            codes.map { code ->
                if (code.lineNumber == 5) {
                    code.copy(variableState = AnnotatedString(" index : $index"))
                } else code
            }
        }
    }

    private fun <T> updateLine6(current: T) {
        _codes.update { codes ->
            codes.map { code ->
                if (code.lineNumber == 6) {
                    code.copy(variableState = AnnotatedString(" current : $current"))
                } else code
            }
        }
    }

    private fun <T> updateLine7N8(current: T, target: T) {
        _codes.update { codes ->
            codes.map { code ->
                if (code.lineNumber == 7) {
                    code.copy(variableState = AnnotatedString(" ( $current == $target ) : ${current == target}"))
                } else code
            }
        }
        _codes.update { codes ->
            codes.map { code ->
                if (code.lineNumber == 8) {
                    code.copy(variableState = AnnotatedString(" ${current == target}"))
                } else code
            }
        }
    }


}

