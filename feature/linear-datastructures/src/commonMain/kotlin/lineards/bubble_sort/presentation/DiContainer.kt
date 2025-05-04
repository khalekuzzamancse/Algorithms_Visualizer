package lineards.bubble_sort.presentation

import lineards.bubble_sort.domain.model.DataModel
import lineards.bubble_sort.inf.InfrastructureFactory

object DiContainer {
    fun <T : Comparable<T>> createSimulator(model: DataModel<T>) =
        InfrastructureFactory.createSimulator(model)
}