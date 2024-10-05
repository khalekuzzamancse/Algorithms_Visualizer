package graphtopologicalsort.di

import graphtopologicalsort.domain.model.GraphModel
import graphtopologicalsort.domain.service.Simulator
import graphtopologicalsort.infrastructure.factory.InfrastructureFactory

object DiContainer {

    fun createSimulator(graph: GraphModel): Simulator =
        InfrastructureFactory.createSimulator(graph)
}