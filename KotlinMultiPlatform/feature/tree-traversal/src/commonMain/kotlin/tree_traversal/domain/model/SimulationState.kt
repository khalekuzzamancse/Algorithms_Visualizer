package tree_traversal.domain.model


sealed interface SimulationState {
    val code:String
    data class ProcessingNode(val node: TreeNode, override val code: String): SimulationState
    data class Finished(override val code: String) : SimulationState
    data class  Misc(override val code: String): SimulationState
}