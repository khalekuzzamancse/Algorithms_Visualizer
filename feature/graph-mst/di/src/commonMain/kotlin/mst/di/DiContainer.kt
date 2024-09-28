package mst.di
import mst.domain.model.DijkstraGraphModel
import mst.domain.service.Simulator
import mst.infrastructure.factory.InfrastructureFactory

object DiContainer {
    fun createSimulator(graph: DijkstraGraphModel): Simulator {
        return  InfrastructureFactory.createSimulator(graph)
    }
}