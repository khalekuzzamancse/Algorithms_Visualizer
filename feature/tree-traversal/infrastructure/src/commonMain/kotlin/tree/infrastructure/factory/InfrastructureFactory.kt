package tree.infrastructure.factory

import tree.domain.model.TraversalType
import tree.domain.model.TreeNode
import tree.domain.service.Simulator

object InfrastructureFactory {
    fun createSimulator(
        root: TreeNode,
        type: TraversalType
    ): Simulator = SimulatorImpl(root,type)

}