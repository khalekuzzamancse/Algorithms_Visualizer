package selectionsort.domain.service

import selectionsort.domain.model.CodeStateModel

object PseudocodeGenerator {
     val rawCode = generate(CodeStateModel())
    fun generate(model: CodeStateModel): String {
        return with(model) {
            """
selectionSort(list) { ${list?.let { "//$it" } ?: ""}
    len = list.size ${len?.let { "// len: $it" } ?: ""}
    for (i = 0; i < len - 1; i++) { ${i?.let { "// i: $it" } ?: ""}
        minIndex = findMinIndex(list, i, len) ${minIndex?.let { "// minIndex: $it" } ?: ""}
        isMinFound = (minIndex != null) ${isMinFound?.let { "// $it" } ?: ""}
        if (isMinFound) ${isMinFound?.let { "// $it" } ?: ""}
            list.swap(i, minIndex) ${swap?.let { "// $it" } ?: ""}
    }
}
""".trimStart()
        }
    }
}
