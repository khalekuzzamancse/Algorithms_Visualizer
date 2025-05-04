package lineards.selection_sort.domain.model

data class DataModel<T:Comparable<T>> (
    val array:List<T>
)