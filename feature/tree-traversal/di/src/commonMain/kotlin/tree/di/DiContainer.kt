package tree.di
import tree.domain.model.TraversalType
import tree.domain.model.TreeNode
import tree.domain.service.Simulator
import tree.infrastructure.factory.InfrastructureFactory

object DiContainer {
    fun createSimulator(
        root:TreeNode,
        type:TraversalType
    ): Simulator {
        return  InfrastructureFactory.createSimulator(root,type)
    }
}