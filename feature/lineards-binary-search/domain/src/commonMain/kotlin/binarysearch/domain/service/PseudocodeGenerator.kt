package binarysearch.domain.service

import binarysearch.domain.model.CodeStateModel

/**
 * - Defining in the domain layer because need to access from both data and ui layer
 */
object PseudocodeGenerator {
    val rawCode = """
    BinarySearch(list, target) {
        low = 0
        high = list.size - 1
        while (low <= high) {
            mid = (low + high) / 2
            current = list[mid]
            isFound=(current == target)
            if (isFound)
                return mid
            else if (current < target)
                low = mid + 1
            else
                high = mid - 1
        }
        return -1
    }
    """.trimStart()

    fun generate(model: CodeStateModel): String {
        return with(model) {
            """
BinarySearch(list, target) { //target: $target
    low = 0 ${low?.let { "//low: $it" } ?: ""} 
    high = list.size - 1 ${high?.let { "//high: $it" } ?: ""}
    while (low <= high) { 
        mid = (low + high) / 2 ${mid?.let { "//mid: $it" } ?: ""} 
        current = list[mid] ${current?.let { "//current: $it" } ?: ""} 
        isFound=(current == target) ${isFound?.let { "//isFound: $it" } ?: ""} 
        if (isFound) ${isFound?.let { "//$it" } ?: ""} 
            return mid
        else if (current < target) ${currentLessThanTarget?.let { "//$it" } ?: ""} 
            low = mid + 1 
        else  ${currentGreaterThanTarget?.let { "//$it" } ?: ""} 
            high = mid - 1 
    }
    return -1
} ${returnIndex?.let { "//returned: $it" } ?: ""} 
""".trimStart()
        }
    }
}

