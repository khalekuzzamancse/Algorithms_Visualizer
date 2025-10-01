package tree_traversal
import tree_traversal.domain.model.TraversalType
import tree_traversal.domain.model.TreeNode
import tree_traversal.domain.service.Simulator
import tree_traversal.inf.InfrastructureFactory

object DiContainer {
    fun createSimulator(
        root: TreeNode,
        type: TraversalType
    ): Simulator {
        return  InfrastructureFactory.createSimulator(root,type)
    }
}