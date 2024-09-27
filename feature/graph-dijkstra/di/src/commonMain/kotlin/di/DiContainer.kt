package di
import domain.model.DijkstraGraphModel
import domain.service.Simulator
import infrastructure.factory.InfrastructureFactory

object DiContainer {
    fun createSimulator(graph: DijkstraGraphModel): Simulator{
        return  InfrastructureFactory.createSimulator(graph)
    }
}