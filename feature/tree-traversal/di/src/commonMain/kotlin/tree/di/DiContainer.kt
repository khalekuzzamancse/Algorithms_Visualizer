package tree.di
import tree.domain.model.DijkstraGraphModel
import tree.domain.service.Simulator
import tree.infrastructure.factory.InfrastructureFactory

object DiContainer {
    fun createSimulator(graph: DijkstraGraphModel): Simulator {
        return  InfrastructureFactory.createSimulator(graph)
    }
}