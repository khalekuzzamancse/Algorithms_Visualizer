package graphtopologicalsort.infrastructure.factory

import graphtopologicalsort.domain.model.GraphModel
import graphtopologicalsort.domain.service.Simulator

object InfrastructureFactory {
    fun createSimulator(graph: GraphModel): Simulator = SimulatorImpl(graph)

}