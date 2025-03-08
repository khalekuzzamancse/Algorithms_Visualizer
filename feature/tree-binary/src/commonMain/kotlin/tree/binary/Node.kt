package tree.binary

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import kotlin.math.max

class Node<T>(val data: T) {
    var left: Node<T>? = null
    var right: Node<T>? = null

    fun getDepth(): Int {
        val leftDepth = left?.getDepth() ?: 0
        val rightDepth = right?.getDepth() ?: 0
        return 1 + max(leftDepth, rightDepth)
    }
}

@Composable
fun TreeView() {
    val tree = remember { initTree() }
    val maxDepth = remember { tree.getDepth() }
    val totalNodes = remember { countNodes(tree) }

    BoxWithConstraints(Modifier.size(400.dp)) {
        val canvasWidth = constraints.maxWidth.toFloat()
        val canvasHeight = constraints.maxHeight.toFloat()

        val (nodes, lines) = remember(tree, canvasWidth, canvasHeight) {
            calculateTreeLayout(tree, canvasWidth, canvasHeight)
        }

        Canvas(Modifier.size(400.dp)) {
            // Draw connecting lines first
            lines.forEach { (start, end) ->
                drawLine(
                    color = Color.Black,
                    start = start,
                    end = end,
                    strokeWidth = 2f
                )
            }

            // Then draw nodes on top
            nodes.forEach { (center, data) ->
                drawNode(center)
            }
        }
    }
}

private fun initTree(): Node<Int> {
    val root = Node(0)
    root.left = Node(1).apply {
        left = Node(3).apply {
            left = Node(7)
            right = Node(8)
        }
        right = tree.binary.Node(4).apply {
            left = tree.binary.Node(7)
            right = tree.binary.Node(8)
        }
    }
    root.right = Node(2).apply {
        left = Node(5).apply {
            left = Node(9)
            right = Node(10)
        }
        right = Node(6).apply {
            left = Node(7)
            right = Node(8)
        }
    }
    return root
}

private fun countNodes(node: Node<Int>?): Int {
    if (node == null) return 0
    return 1 + countNodes(node.left) + countNodes(node.right)
}

private data class NodeLayout(val center: Offset, val data: Int)
private typealias Line = Pair<Offset, Offset>

private fun calculateTreeLayout(
    root: Node<Int>,
    width: Float,
    height: Float
): Pair<List<NodeLayout>, List<Line>> {
    val maxDepth = root.getDepth()
    val nodeRadius = 10f
    val verticalSpacing = if (maxDepth > 0) (height - 2 * nodeRadius) / maxDepth else 0f
    var horizontalSpacing = 0f

    val nodes = mutableListOf<NodeLayout>()
    val lines = mutableListOf<Line>()
    var xIndex = 0

    fun traverse(node: Node<Int>, depth: Int): Pair<Offset, Offset> {
        // Traverse left
        var leftPos: Offset? = null
        node.left?.let {
            val (leftStart, leftEnd) = traverse(it, depth + 1)
            leftPos = leftEnd
            lines.add(leftStart to leftEnd)
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
            lines.add(rightStart to rightEnd)
        }

        // Add connecting lines
        leftPos?.let { lines.add(currentPos to it) }
        rightPos?.let { lines.add(currentPos to it) }

        nodes.add(NodeLayout(currentPos, node.data))
        return currentPos to currentPos
    }

    traverse(root, 0)
    return nodes to lines
}

private fun DrawScope.drawNode(center: Offset) {
    drawCircle(
        color = Color.Black,
        radius = 10f,
        center = center
    )
}