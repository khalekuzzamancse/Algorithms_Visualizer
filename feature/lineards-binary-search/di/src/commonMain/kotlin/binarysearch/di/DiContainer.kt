package binarysearch.di

import binarysearch.domain.model.DataModel
import binarysearch.infrastructure.InfrastructureFactory

object DiContainer {
    fun <T : Comparable<T>> createSimulator(model: DataModel<T>) =
        InfrastructureFactory.createSimulator(model)
}