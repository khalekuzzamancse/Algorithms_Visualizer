package graph

import graph._core.domain.GraphModel
import graph._core.domain.TraversalSimulator
import graph.bfs.inf.BFSBFSSimulatorImpl
import graph.dfs.inf.DFSSimulatorImpl
import graph.prims.domain.service.DijkstraSimulator
import graph.prims.inf.DijkstraSimulatorImpl
import graph.topological_sort.domain.TopologicalSortSimulator
import graph.topological_sort.inf.TopologicalSortSimulatorImpl

internal object DiContainer {
    fun createBFSSimulator(graph: GraphModel): TraversalSimulator = BFSBFSSimulatorImpl(graph)
    fun createDFSSimulator(graph: GraphModel): TraversalSimulator = DFSSimulatorImpl(graph)
    fun createTopologicalSimulator(graph: GraphModel): TopologicalSortSimulator = TopologicalSortSimulatorImpl(graph)
    fun createPrimsSimulator(graph: GraphModel): DijkstraSimulator =
        DijkstraSimulatorImpl(graph.nodes, graph.edges, startNode = graph.source)
}