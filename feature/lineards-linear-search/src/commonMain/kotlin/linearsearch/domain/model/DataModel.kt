package linearsearch.domain.model

data class DataModel<T:Comparable<T>> (
    val array:List<T>,
    val target:T
)