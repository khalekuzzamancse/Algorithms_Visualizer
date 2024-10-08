package selectionsort.di

import selectionsort.domain.model.DataModel
import selectionsort.infrastructure.InfrastructureFactory

object DiContainer {
    fun <T : Comparable<T>> createSimulator(model: DataModel<T>) =
        InfrastructureFactory.createSimulator(model)
}