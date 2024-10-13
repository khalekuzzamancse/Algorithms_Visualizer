package insertionsort.domain.service

import insertionsort.domain.model.CodeStateModel

object PseudocodeGenerator {
    val rawCode = generate(CodeStateModel())

    fun generate(model: CodeStateModel): String {
        return with(model) {
            """
insertionSort(list) { 
    sortedList = list${list?.let { "//$it" } ?: ""}
    len = list.size ${len?.let { "// len: $it" } ?: ""}
    for (i = 1; i < len; i++) { ${i?.let { "// i: $it" } ?: ""}
        for (j = i; j > 0; j--) { ${j?.let { "// j: $it" } ?: ""}
            shouldSwap = sortedList[j] < sortedList[j-1]  { ${valueAtJ?.let { "//$it"}?:""}${valueAtJmiuns1?.let {"<$it"} ?:""}  ${shouldSwap?.let {"->$it"} ?: ""} 
            if (shouldSwap) ${shouldSwap?.let { "// $it" } ?: ""}
                swap(sortedList, j, j-1) ${swap?.let { "// $it" } ?: ""}
            else 
                break
        }
    }
}
""".trimStart()
        }
    }
}
