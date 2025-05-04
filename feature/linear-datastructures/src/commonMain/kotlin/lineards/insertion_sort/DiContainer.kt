package lineards.insertion_sort

import lineards.insertion_sort.domain.model.DataModel
import lineards.insertion_sort.inf.InfrastructureFactory

object DiContainer {
    fun <T : Comparable<T>> createSimulator(model: DataModel<T>) =
        InfrastructureFactory.createSimulator(model)
}