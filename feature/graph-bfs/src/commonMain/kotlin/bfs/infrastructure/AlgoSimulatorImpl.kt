package bfs.infrastructure

import bfs.domain.AlgoSimulator
import bfs.domain.AlgorithmicGraph
import bfs.domain.AlgorithmicNode
import bfs.domain.SimulationState

internal class AlgoSimulatorImpl(
    graph: AlgorithmicGraph,
    startNode: AlgorithmicNode
) : AlgoSimulator {

    private val sequenceBuilder = _BFSSequenceBuilder(graph, startNode)
    private val iterator = sequenceBuilder.iterator.iterator()

    override fun next(): SimulationState {
        return if (iterator.hasNext())
            iterator.next()
        else
            SimulationState.Finished
    }

    @Suppress("Unused")
    private fun log(message: String, methodName: String? = null) {
        val tag = "${this@AlgoSimulatorImpl::class.simpleName}Log:"
        val method = if (methodName == null) "" else "$methodName()'s "
        val msg = "$method:-> $message"
        println("$tag::$msg")
    }

}

@Suppress("ClassName")
private class _BFSSequenceBuilder(
    private val graph: AlgorithmicGraph,
    private val startNode: AlgorithmicNode
) {

    private val pseudocodeBuilder = DebuggablePseudocodeBuilder()
    private var _updatedPseudocode = pseudocodeBuilder.build()//initial code with no debug text


    val iterator: Sequence<SimulationState> = sequence {
        val visited = mutableSetOf<AlgorithmicNode>()
        val queue = ArrayDeque<AlgorithmicNode>()


        //TODO:check the node is exits or note in the graph...user might send a node that does not belongs to this graph

        // Start with the root node
        queue.add(startNode)
        visited.add(startNode)


        updatePseudocode(queue = queue)  //update the queue is initiated
        pushUpdate(SimulationState.Running(pseudocode = _updatedPseudocode))  //push the updated changed



        while (queue.isNotEmpty()) {
            val currentNode = queue.removeFirst()

            //update the a node is processing
            updatePseudocode(currentNode = currentNode, queue = queue)
            pushUpdate(  SimulationState.Running(processingNode = currentNode, pseudocode = _updatedPseudocode))  //push the updated changed


            // Get the adjacent nodes from the graph
            val neighbors = graph.ajacencyList[currentNode] ?: listOf()

            // Visit each neighbor that hasn't been visited
            for (neighbor in neighbors) {
                if (neighbor !in visited) {
                    visited.add(neighbor)
                    queue.add(neighbor)
                }
            }

            updatePseudocode(currentNode = currentNode, queue = queue) //update pseudocode that new node enqueued
            pushUpdate(  SimulationState.Running(processingNode = currentNode, pseudocode = _updatedPseudocode))  //push the updated changed

        }

    }

    private fun updatePseudocode(
        currentNode: AlgorithmicNode? = null,
        queue: ArrayDeque<AlgorithmicNode>? = null,
    ) {
        _updatedPseudocode = pseudocodeBuilder.build(
            PseudoCodeVariablesValue(
                currentNode = currentNode?.label,
                queue = queue?.toString(),
                isQueueEmpty = queue?.isEmpty().toString()
            )
        )

    }

    //TODO: Helper method section

    private suspend fun SequenceScope<SimulationState>.pushUpdate(state: SimulationState){
        yield(state)
    }
    @Suppress("Unused")
    private fun log(message: String, methodName: String? = null) {
        val tag = "${this@_BFSSequenceBuilder::class.simpleName}Log:"
        val method = if (methodName == null) "" else "$methodName()'s "
        val msg = "$method:-> $message"
        println("$tag::$msg")
    }
}
