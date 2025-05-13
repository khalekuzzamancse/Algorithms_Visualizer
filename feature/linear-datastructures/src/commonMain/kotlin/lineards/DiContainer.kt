package lineards

import lineards.linear_search.domain.model.DataModel
import lineards.linear_search.infrastructure.InfrastructureFactory

object DiContainer {
    fun <T : Comparable<T>> createSimulator(model: DataModel<T>) =
        InfrastructureFactory.createSimulator(model)
}