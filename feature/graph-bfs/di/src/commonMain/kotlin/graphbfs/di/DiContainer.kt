package graphbfs.di

import graphbfs.domain.model.GraphModel
import graphbfs.domain.service.Simulator
import graphbfs.infrastructure.factory.InfrastructureFactory

object DiContainer {

    fun createSimulator(graph: GraphModel): Simulator =
        InfrastructureFactory.createSimulator(graph)
}