import graphtraversal.domain.model.GraphModel
import graphtraversal.domain.service.Simulator
import graphtraversal.infrastructure.factory.InfrastructureFactory

object DiContainer {

    fun createSimulator(graph: GraphModel): Simulator =
        InfrastructureFactory.createSimulator(graph)
}