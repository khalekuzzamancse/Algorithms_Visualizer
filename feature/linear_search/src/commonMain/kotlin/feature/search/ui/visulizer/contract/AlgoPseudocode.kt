package feature.search.ui.visulizer.contract

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Need to maintain the single source of truth,so that previous change is stored
 */
internal object AlgoPseudocode {
    private val code: List<Pseudocode.Line> = listOf(
        Pseudocode.Line(
            line = "LinearSearch( list, target )",
            lineNumber = 1,
            indentationLevel = 0,
        ),
        Pseudocode.Line(
            line = "{",
            lineNumber = 2,
            indentationLevel = 0,
        ),
        Pseudocode.Line(
            line = " len = number of items in the list",
            lineNumber = 3,
        ),
        Pseudocode.Line(
            line = " index = 0",
            lineNumber = 4,
        ),

        Pseudocode.Line(
            line = "  WHILE (index < len)",
            lineNumber = 5,
        ),
        Pseudocode.Line(
            line = "current = list[index]",
            lineNumber = 6
        ),
        Pseudocode.Line(
            line = "isFound = (current==target)",
            lineNumber = 7
        ),
        Pseudocode.Line(
            line = "  IF (isFound)",
            lineNumber = 8
        ),
        Pseudocode.Line(
            line = "    {",
            lineNumber = 9
        ),
        Pseudocode.Line(
            line = "      RETURN index",
            lineNumber = 10
        ),
        Pseudocode.Line(
            line = "    }",
            lineNumber = 11
        ),
        Pseudocode.Line(
            line = "    INCREMENT index by 1",
            lineNumber = 12
        ),
        Pseudocode.Line(
            line = "  }",
            lineNumber = 13
        ),
        Pseudocode.Line(
            line = "  RETURN -1",
            lineNumber = 14
        ),
        Pseudocode.Line(
            line = "}",
            lineNumber = 15
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
                    code.copy(variableState = " len : $length")
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
                    code.copy(variableState = " target : $target")
                } else code
            }
        }
    }

    private fun updateLine5(index: Int) {
        _codes.update { codes ->
            codes.map { code ->
                if (code.lineNumber == 5) {
                    code.copy(variableState = " index : $index")
                } else code
            }
        }
    }

    private fun <T> updateLine6(current: T) {
        _codes.update { codes ->
            codes.map { code ->
                if (code.lineNumber == 6) {
                    code.copy(variableState = " current : $current")
                } else code
            }
        }
    }

    private fun <T> updateLine7N8(current: T, target: T) {
        _codes.update { codes ->
            codes.map { code ->
                if (code.lineNumber == 7) {
                    code.copy(variableState = " ( $current == $target ) : ${current == target}")
                } else code
            }
        }
        _codes.update { codes ->
            codes.map { code ->
                if (code.lineNumber == 8) {
                    code.copy(variableState = " ${current == target}")
                } else code
            }
        }
    }

    /**
     * Takes list(line number,state:String)
     */
    fun updateVariableState(
        states: List<Pair<Int, String?>>,
    ) {
        _codes.update { codes ->
            codes.map { line ->
                val updatedLine = states.find { it.first == line.lineNumber }
                if (updatedLine != null) {
                    line.copy(variableState = updatedLine.second)
                } else {
                    line.copy(variableState = null)
                }
            }
        }

    }


}

