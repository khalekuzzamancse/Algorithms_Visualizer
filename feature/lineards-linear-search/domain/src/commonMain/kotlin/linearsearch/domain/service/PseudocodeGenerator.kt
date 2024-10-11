package linearsearch.domain.service

import linearsearch.domain.model.PseudocodeModel

/**
 * - Defining in the domain layer because need to access from both data and ui layer
 */
object PseudocodeGenerator {
    val rawCode = """
    LinearSearch(list, target) {
        len = list.size
        for (i = 0; i < len; i++) {
            current = list[index]
            isFound = (current == target)
            if (isFound)
                return index
        }
        return -1
    }
    """

    fun generate(model: PseudocodeModel): String {
        return with(model) {
            """
LinearSearch(list, target) { //target: $target
    len = list.size //len: $len
    for (i = 0; i < len; i++) { ${i?.let { "//i: $it" } ?: ""}
        current = list[index] ${current?.let { "//current: $it" } ?: ""} 
        isFound = (current == target) 
        if (isFound) ${isFound?.let { "//isFound: $it" } ?: ""} 
            return index
    }
    return -1
} ${returnIndex?.let { "//returned: $it" } ?: ""} 
"""
        }
    }

}