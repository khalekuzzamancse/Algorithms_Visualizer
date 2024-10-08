package insertionsort.di

import insertionsort.domain.model.DataModel
import insertionsort.infrastructure.InfrastructureFactory

object DiContainer {
    fun <T : Comparable<T>> createSimulator(model: DataModel<T>) =
        InfrastructureFactory.createSimulator(model)
}