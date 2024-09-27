package topological_sort.domain

internal sealed interface SimulationState {
    data class Running(
        val processingNode: AlgorithmicNode?=null,
        val pseudocode: List<LineForPseudocode>,
        val peekNode: AlgorithmicNode?=null,
    ): SimulationState
    data object Finished: SimulationState
}