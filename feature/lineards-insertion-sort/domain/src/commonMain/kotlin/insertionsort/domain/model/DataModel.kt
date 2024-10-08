package insertionsort.domain.model

data class DataModel<T:Comparable<T>> (
    val array:List<T>
)