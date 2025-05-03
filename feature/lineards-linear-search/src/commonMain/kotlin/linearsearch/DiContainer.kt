package linearsearch

import linearsearch.domain.model.DataModel
import linearsearch.infrastructure.InfrastructureFactory

object DiContainer {
    fun <T : Comparable<T>> createSimulator(model: DataModel<T>) =
        InfrastructureFactory.createSimulator(model)
}