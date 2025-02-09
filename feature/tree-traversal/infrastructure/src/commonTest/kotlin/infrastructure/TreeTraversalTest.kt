package infrastructure

import tree.domain.model.TraversalType
import tree.domain.model.TreeNode
import tree.infrastructure.factory.Traversal
import kotlin.test.Test
import kotlin.test.fail

class TreeTraversalTest {
    @Test
    fun test() {
        val nodeA = TreeNode("A")
        val nodeB = TreeNode("B")
        val nodeC = TreeNode("C")
        val nodeD = TreeNode("D")
        val nodeE = TreeNode("E")

        nodeA.children.addAll(listOf(nodeB, nodeC))
        nodeB.children.add(nodeD)
        nodeC.children.add(nodeE)

        val traversal = Traversal(nodeA,TraversalType.DFS)

        val visitedOrder = traversal.traverse()
        for (node in visitedOrder) {
            println(node.id)
        }

        fail()
    }
}
