package graphtraversal.infrastructure.factory

import graphtraversal.domain.model.GraphModel
import graphtraversal.domain.service.Simulator

object InfrastructureFactory {
    fun createSimulator(graph: GraphModel): Simulator = SimulatorImpl(graph)

}