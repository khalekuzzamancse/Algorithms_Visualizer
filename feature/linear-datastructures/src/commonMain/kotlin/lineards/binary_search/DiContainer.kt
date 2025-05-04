package lineards.binary_search

import lineards.binary_search.domain.model.DataModel
import lineards.binary_search.infrastructure.InfrastructureFactory

object DiContainer {
    fun <T : Comparable<T>> createSimulator(model: DataModel<T>) =
        InfrastructureFactory.createSimulator(model)
}