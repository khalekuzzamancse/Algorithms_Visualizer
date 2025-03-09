package tree.binary.tree_view

import androidx.compose.ui.geometry.Offset
interface  LayoutAlgorithm<T>{
    fun calculateTreeLayout(root: Node<T>, width: Float, height: Float):VisualTree
    companion object{
        fun <T:Comparable<T>>create():LayoutAlgorithm<T>{
            return  DonaldKnuthAlgorithm()
        }
    }
}



private class DonaldKnuthAlgorithm<T:Comparable<T>> :LayoutAlgorithm<T>{
    override fun calculateTreeLayout(root: Node<T>, width: Float, height: Float): VisualTree {
        val maxDepth = root.getDepth()
        val nodeRadius = 10f
        val verticalSpacing = if (maxDepth > 0) (height - 2 * nodeRadius) / maxDepth else 0f
        var horizontalSpacing = 0f

        val nodes = mutableListOf<NodeLayout>()
        val visualLines = mutableListOf<VisualLine>()
        var xIndex = 0

        fun traverse(node: Node<T>, depth: Int): Pair<Offset, Offset> {
            // Traverse left
            var leftPos: Offset? = null
            node.left?.let {
                val (leftStart, leftEnd) = traverse(it, depth + 1)
                leftPos = leftEnd
                visualLines.add(VisualLine(leftStart, leftEnd))
            }

            // Calculate current position
            if (horizontalSpacing == 0f) {
                val totalWidthNodes = countNodes(root).toFloat()
                horizontalSpacing = (width - 2 * nodeRadius) / (totalWidthNodes - 1)
            }
            val x = nodeRadius + xIndex * horizontalSpacing
            val y = nodeRadius + depth * verticalSpacing
            val currentPos = Offset(x, y)
            xIndex++

            // Traverse right
            var rightPos: Offset? = null
            node.right?.let {
                val (rightStart, rightEnd) = traverse(it, depth + 1)
                rightPos = rightEnd
                visualLines.add(VisualLine(rightStart, rightEnd))
            }

            // Add connecting lines
            leftPos?.let { visualLines.add(VisualLine(currentPos, it)) }
            rightPos?.let { visualLines.add(VisualLine(currentPos, it)) }

            nodes.add(NodeLayout(currentPos, node.label, node.id))
            return currentPos to currentPos
        }

        traverse(root, 0)
        return  VisualTree(nodes ,visualLines)
    }

    private fun countNodes(node: Node<T>?): Int {
        if (node == null) return 0
        return 1 + countNodes(node.left) + countNodes(node.right)
    }

}



