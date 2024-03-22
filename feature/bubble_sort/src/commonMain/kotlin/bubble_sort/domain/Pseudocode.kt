package bubble_sort.domain

import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Used as separate from other module pseudocode so that
 * that module can easily detach from other module
 */


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
            line = AnnotatedString("bubbleSort( list ) {"),
            lineNumber = 1,
            indentationLevel = 0,
        ),
        Pseudocode.Line(
            line = AnnotatedString("len = list.size"),
            lineNumber = 2,
            indentationLevel = 1,
        ),
        Pseudocode.Line(
            line = AnnotatedString("for ( i=0 ; i<len ; i++ ) {"),
            lineNumber = 3,
            indentationLevel = 1,
            topPaddingLevel = 1
        ),
        Pseudocode.Line(
            line = AnnotatedString(" for ( int j = 0; j < len-i-1; j++ ) {"),
            lineNumber = 4,
            indentationLevel = 2,
        ),
        Pseudocode.Line(
            line = AnnotatedString("shouldSwap = list[j] > list[j+1]"),
            lineNumber = 5,
            indentationLevel = 2,
        ),
        Pseudocode.Line(
            line = AnnotatedString("if ( shouldSwap )"),
            lineNumber = 6,
            indentationLevel = 2,
        ),
        Pseudocode.Line(
            line = AnnotatedString("swap( [list[j], list[j+1] )"),
            lineNumber = 7,
            indentationLevel = 3,
        ),
        Pseudocode.Line(
            line = AnnotatedString("    }"),
            lineNumber = 13,
            indentationLevel = 1,
            topPaddingLevel = 1
        ),

        Pseudocode.Line(
            line = AnnotatedString("}"),
            lineNumber = 14,
            indentationLevel = 1,
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
        len: Int? = null,
        i: Int? = null,
        j: Int? = null,
        jValue: T? = null,
        jPlus1Value: T? = null
    ) {

        if (len != null) updateLine2(len) else hideStateOfLine(2)
        if (i != null) updateLine3(i) else hideStateOfLine(3)
        if (j != null) updateLine4(j) else hideStateOfLine(4)

        if (jValue != null && jPlus1Value != null){
            updateLine5(jValue, jPlus1Value) //list[j]]?list[j]
            updateLine6(jValue, jPlus1Value) //shouldSwap
            val swapNeed=jValue>jPlus1Value
            if (swapNeed)
                updateLine7(jValue, jPlus1Value)

        }
        else{
            hideStateOfLine(5)
            hideStateOfLine(6)
            hideStateOfLine(7)
        }


    }

    private fun updateLine2(len: Int) = updateLine(2, AnnotatedString(" len : $len"))
    private fun updateLine3(i: Int) = updateLine(3, AnnotatedString(" i : $i"))
    private fun updateLine4(j: Int) = updateLine(4, AnnotatedString(" j : $j"))
    private fun<T : Comparable<T>> updateLine5(jValue: T, jPlus1Value: T) =
        updateLine(5, AnnotatedString(" $jValue > $jPlus1Value : ${jValue > jPlus1Value}"))

    private fun <T : Comparable<T>> updateLine6(jValue: T, jPlus1Value: T) =
        updateLine(6, AnnotatedString(" ${jValue > jPlus1Value}"))

    private fun <T : Comparable<T>> updateLine7(jValue: T, jPlus1Value: T) =
        updateLine(7, AnnotatedString("swap ( $jValue , $jPlus1Value )"))

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

