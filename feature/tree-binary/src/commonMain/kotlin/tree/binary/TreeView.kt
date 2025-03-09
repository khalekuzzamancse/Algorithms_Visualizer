package tree.binary

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import kotlin.math.max


@Composable
fun <T> TreeView(tree: Node<T>) {
    val treeUtil = remember(tree) { TreeUtil(tree) }
    val textMeasurer = rememberTextMeasurer()

    BoxWithConstraints(Modifier.padding(20.dp).size(400.dp)) {
        val canvasWidth = constraints.maxWidth.toFloat()
        val canvasHeight = constraints.maxHeight.toFloat()

        val (nodes, lines) = remember(tree, canvasWidth, canvasHeight) {
            treeUtil.calculateTreeLayout(tree, canvasWidth, canvasHeight)
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
            nodes.forEach { node ->
                drawNode(center = node.center, label = node.label, measurer = textMeasurer)
            }
        }
    }
}

private fun DrawScope.drawNode(center: Offset, label: String, measurer: TextMeasurer) {
    
        drawCircle(
            color = Color.Blue,
            radius = 20f,
            center = center
        )
        val move = Offset(
            measurer.measure(label).size.width / 2f,
            measurer.measure(label).size.height / 2f
        )
        drawText(
            textMeasurer = measurer,
            text = label,
            topLeft = center - move,
            style = TextStyle(color = Color.White)
        )


}


private typealias Line = Pair<Offset, Offset>
//TODO: Need at least two nodes otherwise causes exception, need to handle that edge case
class BST<T : Comparable<T>>( val root: Node<T>?) {

    fun insert(value: T): BST<T> {
        return BST(insertRecursive(root, value))
    }

    private fun insertRecursive(node: Node<T>?, value: T): Node<T> {
        return when {
            node == null -> Node(value) // Create a new node if null
            value < node.data -> Node(node.data, insertRecursive(node.left, value), node.right) // Copy with left update
            value > node.data -> Node(node.data, node.left, insertRecursive(node.right, value)) // Copy with right update
            else -> node // Return same node if value already exists
        }
    }
}
data class Node<T>(val data: T,   val left: Node<T>? = null,val right: Node<T>? = null) {

    fun getDepth(): Int {
        val leftDepth = left?.getDepth() ?: 0
        val rightDepth = right?.getDepth() ?: 0
        return 1 + max(leftDepth, rightDepth)
    }

    val label = "$data"
}

data class NodeLayout(val center: Offset, val label: String)

/**
 * Donald Knuth's binary tree drawing algorithm.
 * https://gist.github.com/deepankarb/4ae7c2a88ed6e9a8a1c636e0c1062f07
 * https://llimllib.github.io/pymag-trees/
 * Example:
 * ```kotlin
 * fun createTree(): Node<Int> {
 *     val root = Node(0)
 *     root.left = Node(1).apply {
 *         left = Node(3).apply {
 *             left = Node(7)
 *             right = Node(8)
 *         }
 *         right = Node(4).apply {
 *             left = Node(7)
 *             right = Node(8)
 *         }
 *     }
 *     root.right = Node(2).apply {
 *         left = Node(5).apply {
 *             left = Node(9)
 *             right = Node(10)
 *         }
 *         right = Node(6).apply {
 *             left = Node(7)
 *             right = Node(8)
 *         }
 *     }
 *     return root
 * }
 *
 *  TreeView(createTree())
 * ```
 */
//TODO: Need at least two nodes otherwise causes exception, need to handle that edge case

class TreeUtil<T>(
    val tree: Node<T>
) {

    private fun countNodes(node: Node<T>?): Int {
        if (node == null) return 0
        return 1 + countNodes(node.left) + countNodes(node.right)
    }

    fun calculateTreeLayout(
        root: Node<T>,
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

        fun traverse(node: Node<T>, depth: Int): Pair<Offset, Offset> {
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

            nodes.add(NodeLayout(currentPos, node.label))
            return currentPos to currentPos
        }

        traverse(root, 0)
        return nodes to lines
    }
}






