package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.dto.non_database


class LinearSearchPseudocode {
    fun highLightPseudocode(lineNumber: Int): List<PseudoCodeLine> {
        return code.map {
            if (it.lineNumber == lineNumber)
                it.copy(highLighting = true)
            else it.copy(highLighting = false)
        }
    }

    val code = listOf(
        PseudoCodeLine(
            line = "LinearSearch(list, target_element)",
            lineNumber = 1
        ),
        PseudoCodeLine(
            line = "{",
            lineNumber = 2
        ),
        PseudoCodeLine(
            line = "  INITIALIZE index = 0",
            lineNumber = 3
        ),
        PseudoCodeLine(
            line = "  WHILE (index < number of items in the list)",
            lineNumber = 4
        ),
        PseudoCodeLine(
            line = "    IF (list[index] == target element)",
            lineNumber = 5
        ),
        PseudoCodeLine(
            line = "    {",
            lineNumber = 6
        ),
        PseudoCodeLine(
            line = "      RETURN index",
            lineNumber = 7
        ),
        PseudoCodeLine(
            line = "    }",
            lineNumber = 8
        ),
        PseudoCodeLine(
            line = "    INCREMENT index by 1",
            lineNumber = 9
        ),
        PseudoCodeLine(
            line = "  }",
            lineNumber = 10
        ),
        PseudoCodeLine(
            line = "  RETURN -1",
            lineNumber = 11
        ),
        PseudoCodeLine(
            line = "}",
            lineNumber = 12
        )
    )
}