@file:Suppress("unused","className","functionName")
package tree.binary.tree_view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import core.lang.Logger
import java.util.Stack
import kotlin.math.max

@Composable
fun <T : Comparable<T>> TreeViewOld(tree: Node<T>) {
    val treeUtil = remember(tree) { _TreeUtil(tree) }
    val density = LocalDensity.current
    val size = 50.dp
    val offset = with(density) {
        Offset(size.toPx(), size.toPx()).div(2f)
    }

    BoxWithConstraints(Modifier.padding(20.dp).size(400.dp)) {
        val canvasWidth = constraints.maxWidth.toFloat()
        val canvasHeight = constraints.maxHeight.toFloat()

        val (nodes, lines) = remember(tree, canvasWidth, canvasHeight) {
            treeUtil.calculateTreeLayout(tree, canvasWidth, canvasHeight)
        }
        Box(Modifier.size(400.dp).drawBehind {
            lines.forEach { (start, end) ->
                drawLine(
                    color = Color.Black,
                    start = start,
                    end = end,
                    strokeWidth = 2f
                )
            }
        }) {
            nodes.forEach { node ->
                _VisualNode(label = node.label, size = size, offset = node.center - offset)
            }

        }
    }
}

/**
 * Has a bug, so at least two nodes required for drawing
 */
@Composable
private fun <T> TreeViewBasic(tree: Node<T>) {
    val treeUtil = remember(tree) { _TreeUtil(tree) }
    val textMeasurer = rememberTextMeasurer()

    BoxWithConstraints(Modifier.padding(20.dp).size(400.dp)) {
        val canvasWidth = constraints.maxWidth.toFloat()
        val canvasHeight = constraints.maxHeight.toFloat()

        val (nodes, lines) = remember(tree, canvasWidth, canvasHeight) {
            treeUtil.calculateTreeLayout(tree, canvasWidth, canvasHeight)
        }
        Canvas(Modifier.size(400.dp)) {
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

/**
 * Immutable, return return re-assign the new tree and update the root to cause render
 */
class BST<T : Comparable<T>>(val root: Node<T>?) {

    fun insert(value: T): BST<T> {
        val newRoot = insertRecursive(root, value)
        return BST(newRoot)
    }

    private fun insertRecursive(node: Node<T>?, value: T): Node<T> {
        if (node != null) {
            Logger.on(this.javaClass.simpleName, "${node.data}")
        }
        return when {
            node == null -> Node(value) // Create a new node if null
            value < node.data -> Node(
                node.data,
                insertRecursive(node.left, value),
                node.right
            ) // Copy with left update
            value > node.data -> Node(
                node.data,
                node.left,
                insertRecursive(node.right, value)
            ) // Copy with right update
            else -> node // Return same node if value already exists
        }
    }

    private fun insertIterative(root: Node<T>?, value: T): Node<T> {
        if (root == null) return Node(value)
        val stack = Stack<Node<T>>() // Stack for traversal
        val path = Stack<Node<T>>() // Stack to rebuild immutable nodes

        var current = root
        while (current != null) {
            Logger.on(this.javaClass.simpleName, "${current.data}")
            stack.push(current)

            current = when {
                value < current.data -> current.left
                value > current.data -> current.right
                else -> return root // Value already exists, return original tree
            }
        }

        // Insert new node at the correct position
        var newNode = Node(value)

        // Rebuild the immutable tree from the stack
        while (stack.isNotEmpty()) {
            val parent = stack.pop()
            newNode = if (value < parent.data) {
                Node(parent.data, newNode, parent.right) // Recreate with updated left child
            } else {
                Node(parent.data, parent.left, newNode) // Recreate with updated right child
            }
            path.push(newNode)
        }

        return path.pop() // New root of the immutable tree
    }

}

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

private data class _Node<T>(
    val data: T,
    val left: Node<T>? = null,
    val right: Node<T>? = null,
    val id: String = "$data"
) {

    fun getDepth(): Int {
        val leftDepth = left?.getDepth() ?: 0
        val rightDepth = right?.getDepth() ?: 0
        return 1 + max(leftDepth, rightDepth)
    }

    val label = "$data"
}

private data class _NodeLayout(
    val center: Offset,
    val label: String,
    val id: String,
    val color: Color = Color.Blue
)

private class _TreeUtil<T>(
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
    ): Pair<List<NodeLayout>, List<VisualLine>> {
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
        return nodes to visualLines
    }
}




@Composable
private fun _VisualNode(
    label:String,
    size: Dp = 50.dp,
    offset: Offset= Offset.Zero,
    color: Color=Color.Blue
) {
    //val offsetAnimation by animateOffsetAsState(offset, label = "")
    val padding = 8.dp
    Box(
        modifier = Modifier
            .size(size)
            .offset {
                IntOffset(offset.x.toInt(), offset.y.toInt())
            }
    ) {
        Text(
            text = label,
            style = TextStyle(color = Color.White),
            modifier = Modifier
                .padding(padding)
                .clip(CircleShape)
                .background(color)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}


