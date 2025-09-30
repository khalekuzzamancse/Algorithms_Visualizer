package lineards.bubble_sort.domain.service

import lineards.bubble_sort.domain.model.CodeStateModel

/**
 * - Defining in the domain layer because need to access from both data and ui layer
 */
object PseudocodeGenerator {
    val rawCode = """
    bubbleSort(list) {
        sortedList=list
        len = sortedList.size
        for (i = 0; i < len; i++) {
            for (j = 0; j < len-i-1; j++) {
                shouldSwap = sortedList[j] > sortedList[j+1]
                if (shouldSwap)
                    swap(sortedList[j], sortedList[j+1])
            }
        }
    }
    """.trimStart()

    fun generate(model: CodeStateModel): String {
        return with(model) {
            """
bubbleSort(list) { 
    sortedList=list  ${sortedList?.let { "// $it" } ?: ""} 
    len = list.size ${len?.let { "// len: $it" } ?: ""} 
    for (i = 0; i < len; i++) { ${i?.let { "// i: $it" } ?: ""} 
        for (j = 0; j < len-i-1; j++) { ${j?.let { "// j: $it" } ?: ""} 
            shouldSwap = list[j] > list[j+1] { ${valueAtJ?.let { "//$it"}?:""}${valueAtJPlus1?.let {">$it"} ?:""}  ${shouldSwap?.let {"->$it"} ?: ""} 
            if (shouldSwap) ${shouldSwap?.let { "// $it" } ?: ""} 
                swap(list[j], list[j+1]) ${swap?.let { "//$it" } ?: ""}
        }
    }
} 
""".trimStart()
        }
    }
}
