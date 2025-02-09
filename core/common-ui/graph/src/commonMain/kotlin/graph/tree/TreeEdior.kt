package graph.tree

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import core.lang.Logger
import graph.graph.common.model.GraphResult
import graph.graph.common.model.Node
import graph.graph.editor.factory.SavedGraphProvider
import graph.graph.editor.model.GraphType
import graph.graph.editor.ui.GraphEditor

@Composable
fun TreeEditor(
    navigationIcon:@Composable ()->Unit,
    onDone: (TreeResult) -> Unit,
){
    val  tag= remember { "TreeEditor" }
    GraphEditor(
        navigationIcon = navigationIcon,
        graphType = GraphType.Undirected,//Tree's are undirected Graph
        initialGraph = SavedGraphProvider.getTree(),
        onDone = {result ->
            val root= buildTree(result)
            onDone(
                TreeResult(
                    controller = result.controller,
                    root = root
                )
            )
            Logger.always(tag,"Tree:$root")
        }
    )
}


fun buildTree(graphResult: GraphResult): TreeNode {
    // Map of node ID to TreeNode for quick lookup
    val treeNodeMap: Map<String, TreeNode> = graphResult.nodes.associateBy({ it.id }) { TreeNode(it) }

    // Set to keep track of all child node IDs
    val childIds = mutableSetOf<String>()

    // Establish parent-child relationships based on edges
    for (edge in graphResult.edges) {
        val parentTreeNode = treeNodeMap[edge.from.id]
        val childTreeNode = treeNodeMap[edge.to.id]

        if (parentTreeNode != null && childTreeNode != null) {
            parentTreeNode.children.add(childTreeNode)
            childIds.add(edge.to.id)
        } else {
            throw IllegalArgumentException("Invalid edge with from ID: ${edge.from.id} or to ID: ${edge.to.id}")
        }
    }

    // Identify the root node (the one that's not a child of any node)
    val rootNode = graphResult.nodes.find { it.id !in childIds }
        ?: throw IllegalArgumentException("No root found. The graph might not be a tree.")

    // Return the corresponding TreeNode
    return treeNodeMap[rootNode.id]!!
}
