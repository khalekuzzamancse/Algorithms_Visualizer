package graph

import graph._core.domain.GraphModel
import graph._core.domain.TraversalSimulator
import graph.bfs.inf.BFSBFSSimulatorImpl
import graph.dfs.inf.DFSSimulatorImpl
import graph.djkstra.domain.model.DijkstraGraphModel
import graph.djkstra.domain.service.Simulator
import graph.djkstra.inf.InfrastructureFactory
import graph.prims.domain.service.PrimsSimulator
import graph.prims.inf.PrimsSimulatorImpl
import graph.topological_sort.domain.TopologicalSortSimulator
import graph.topological_sort.inf.TopologicalSortSimulatorImpl

internal object DiContainer {
    fun createBFSSimulator(graph: GraphModel): TraversalSimulator = BFSBFSSimulatorImpl(graph)
    fun createDFSSimulator(graph: GraphModel): TraversalSimulator = DFSSimulatorImpl(graph)
    fun createTopologicalSimulator(graph: GraphModel): TopologicalSortSimulator = TopologicalSortSimulatorImpl(graph)
    fun createPrimsSimulator(graph: GraphModel): PrimsSimulator =
        PrimsSimulatorImpl(graph.nodes, graph.edges, startNode = graph.source)
    fun createSimulator(graph: DijkstraGraphModel): Simulator {
        return  InfrastructureFactory.createSimulator(graph)
    }
}