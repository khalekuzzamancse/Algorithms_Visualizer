package graphbfs.infrastructure.factory

import graphbfs.domain.model.GraphModel
import graphbfs.domain.service.Simulator

object InfrastructureFactory {
    fun createSimulator(graph: GraphModel): Simulator = SimulatorImpl(graph)

}