package lineards.insertion_sort.domain.model

data class DataModel<T:Comparable<T>> (
    val array:List<T>
)