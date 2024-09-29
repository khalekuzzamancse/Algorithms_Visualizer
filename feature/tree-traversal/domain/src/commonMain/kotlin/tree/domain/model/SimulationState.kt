package tree.domain.model


sealed interface SimulationState {
    data class ProcessingNode(val node: TreeNode): SimulationState
    data object Finished : SimulationState
}