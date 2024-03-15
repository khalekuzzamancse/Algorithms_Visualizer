package selection_sort.ui.visulizer.contract

internal class AlgoPseudocode {
    fun highLightPseudocode(lineNumber: Int): List<Pseudocode.Line> {
        return code.map {
            if (it.lineNumber == lineNumber)
                it.copy(highLighting = true)
            else it
        }
    }

    val code = listOf(
        Pseudocode.Line(
            line = "LinearSearch(list, target_element)",
            lineNumber = 1
        ),
        Pseudocode.Line(
            line = "{",
            lineNumber = 2
        ),
        Pseudocode.Line(
            line = "  INITIALIZE index = 0",
            lineNumber = 3
        ),
        Pseudocode.Line(
            line = "  WHILE (index < number of items in the list)",
            lineNumber = 4
        ),
        Pseudocode.Line(
            line = "    IF (list[index] == target element)",
            lineNumber = 5
        ),
        Pseudocode.Line(
            line = "    {",
            lineNumber = 6
        ),
        Pseudocode.Line(
            line = "      RETURN index",
            lineNumber = 7
        ),
        Pseudocode.Line(
            line = "    }",
            lineNumber = 8
        ),
        Pseudocode.Line(
            line = "    INCREMENT index by 1",
            lineNumber = 9
        ),
        Pseudocode.Line(
            line = "  }",
            lineNumber = 10
        ),
        Pseudocode.Line(
            line = "  RETURN -1",
            lineNumber = 11
        ),
        Pseudocode.Line(
            line = "}",
            lineNumber = 12
        )
    )
}

