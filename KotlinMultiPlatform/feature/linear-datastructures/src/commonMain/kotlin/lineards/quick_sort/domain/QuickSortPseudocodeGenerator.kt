package lineards.quick_sort.domain


object QuickSortPseudocodeGenerator {

    val rawCode = """
    quickSort(arr, low, high) {
        if (low < high) {
            pivotIndex = partition(arr, low, high)
            quickSort(arr, low, pivotIndex - 1)
            quickSort(arr, pivotIndex + 1, high)
        }
    }

    partition(arr, low, high) {
        pivot = arr[high]
        i = low - 1
        for (j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++
                swap(arr[i], arr[j])
            }
        }
        swap(arr[i+1], arr[high])
        return i + 1
    }
    """.trimIndent()

    fun generate(state: QuickSortState): String {
        if (state is QuickSortState.Finish) return rawCode

        state as QuickSortState.State
        val swappedComment = state.swapped?.let { "// swapped(${it.first}, ${it.second})" } ?: ""

        return """
quickSort(arr, low, high) { 
    if (low < high) { ${"// low: ${state.low}, high: ${state.high}"} 
        pivotIndex = partition(arr, low, high) 
        quickSort(arr, low, pivotIndex - 1) 
        quickSort(arr, pivotIndex + 1, high) 
    }
}

partition(arr, low, high) { ${"// low: ${state.low}, high: ${state.high}"} 
    pivot = arr[high] ${"// pivot index: ${state.high}, value: pivot"} 
    i = low - 1 ${state.i?.let { "// i: $it" } ?: ""} 
    for (j = low; j < high; j++) { ${state.j?.let { "// j: $it" } ?: ""} 
        if (arr[j] <= pivot) { 
            i++ ${state.i?.let { "// i: $it" } ?: ""} 
            swap(arr[i], arr[j]) $swappedComment
        }
    }
    swap(arr[i+1], arr[high]) $swappedComment
    return i + 1
}
""".trimIndent()
    }
}
