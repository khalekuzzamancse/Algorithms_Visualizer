package tree_traversal.inf

import tree_traversal.domain.model.TraversalType
import tree_traversal.domain.model.TreeNode
import tree_traversal.domain.service.Simulator

object InfrastructureFactory {
    fun createSimulator(
        root: TreeNode,
        type: TraversalType
    ): Simulator = SimulatorImpl(root,type)

}