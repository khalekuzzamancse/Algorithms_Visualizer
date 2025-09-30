package tree.binary.expression_tree

import core.lang.Logger
import tree.binary.core.BaseNode
import tree.binary.tree_view.Node
import java.util.Stack

class ExpressionTreeIterator {
    private val builder: ExpressionTreeBuilder = ExpressionTreeBuilder.create()

    fun buildIterator(expression: String) = buildExpressionTree(expression).iterator()

    /**Takes postfix tree*/
    private fun buildExpressionTree(expression: String): Sequence<Node<String>?> = sequence {
        val postfix = builder.buildPostfix(expression)
        val stack = Stack<Node<String>>()
        for (token in postfix) {
            val node = if (isOperator(token)) {
                val right = stack.removeLastOrNull() ?: return@sequence
                yield(Node(data = token, right = right))
                val left = stack.removeLastOrNull() ?: return@sequence
                Node(data = token, left = left, right = right)
            } else {
                Node(data = token, left = null, right = null)
            }
            stack.push(node)
            yield(node)
        }
        yield(stack.lastOrNull()) //Peek or Null
        return@sequence
    }

    private fun isOperator(token: String): Boolean = token in setOf("+", "-", "*", "/", "^")
}

interface ExpressionTreeBuilder {
    fun buildTree(expression: String): Node<String>?
    fun buildTree2(expression: String): BaseNode?
    fun buildPostfix(expression: String): List<String>
    fun buildInfix(expression: String): List<String>

    companion object {
        fun create(): ExpressionTreeBuilder = ExpressionTreeBuilderImpl()
    }
}


private class ExpressionTreeBuilderImpl : ExpressionTreeBuilder {

    override fun buildTree(expression: String): Node<String>? {
        val tokens = tokenize(expression)
        val postfix = infixToPostfix(tokens)
        val tree = buildExpressionTree(postfix)
        return tree
    }

    override fun buildTree2(expression: String): BaseNode? {
        val tokens = tokenize(expression)
        val postfix = infixToPostfix(tokens)
        val tree = buildExpressionTree2(postfix)
        return tree
    }

    override fun buildPostfix(expression: String): List<String> {
        val tokens = tokenize(expression)
        return infixToPostfix(tokens)
    }


    override fun buildInfix(expression: String): List<String> {
        return tokenize(expression)
    }

    private fun tokenize(expression: String): List<String> {
        val tokens = mutableListOf<String>()
        val number = StringBuilder()
        for (char in expression.replace(" ", "")) {
            when {
                char.isDigit() -> number.append(char)
                else -> {
                    if (number.isNotEmpty()) {
                        tokens.add(number.toString())
                        number.clear()
                    }
                    tokens.add(char.toString())
                }
            }
        }
        if (number.isNotEmpty()) {
            tokens.add(number.toString())
        }
        return tokens
    }

    private fun infixToPostfix(tokens: List<String>): List<String> {
        val output = mutableListOf<String>()
        val opStack = ArrayDeque<String>()
        val precedence = mapOf("+" to 1, "-" to 1, "*" to 2, "/" to 2, "^" to 3)
        val associativity = mapOf(
            "+" to "left", "-" to "left", "*" to "left", "/" to "left", "^" to "right"
        )

        for (token in tokens) {
            when {
                token == "(" -> opStack.addLast(token)
                token == ")" -> {
                    while (opStack.isNotEmpty() && opStack.last() != "(") {
                        output.add(opStack.removeLast())
                    }
                    opStack.removeLast() // Remove '('
                }

                isOperator(token) -> {
                    while (opStack.isNotEmpty() && opStack.last() != "(" &&
                        (precedence[token]!! < precedence[opStack.last()]!! ||
                                (precedence[token] == precedence[opStack.last()] && associativity[token] == "left"))
                    ) {
                        output.add(opStack.removeLast())
                    }
                    opStack.addLast(token)
                }

                else -> output.add(token) // Operand
            }
        }
        while (opStack.isNotEmpty()) {
            output.add(opStack.removeLast())
        }
        return output
    }

    private fun buildExpressionTree2(postfix: List<String>): BaseNode? {
        val stack = Stack<BaseNode>()

        postfix.forEachIndexed { index, token ->
            if (isOperator(token)) {
                val right = stack.removeLastOrNull() ?: return null
                val left = stack.removeLastOrNull() ?: return null
                stack.push(BaseNode(label = token, id = "$index", left = left, right = right))
            } else {
                stack.push(BaseNode(label = token, id = "$index", left = null, right = null))
            }
        }
        return stack.peek()

//        val stack = Stack<BaseNode>()
//        for (token in postfix) {
//            if (isOperator(token)) {
//                val right = stack.removeLastOrNull() ?: return null
//                val left = stack.removeLastOrNull() ?: return null
//                stack.push(BaseNode(label = token, left = left, right = right))
//            } else {
//                stack.push(BaseNode(label = token, left = null,right = null))
//            }
//        }
//        return stack.peek()
    }

    private fun buildExpressionTree(postfix: List<String>): Node<String>? {
        val stack = Stack<Node<String>>()
        for (token in postfix) {
            if (isOperator(token)) {
                val right = stack.removeLastOrNull() ?: return null
                val left = stack.removeLastOrNull() ?: return null
                stack.push(Node(data = token, left = left, right = right))
            } else {
                stack.push(Node(data = token, left = null, right = null))
            }
        }
        return stack.peek()
    }

    private fun isOperator(token: String): Boolean = token in setOf("+", "-", "*", "/", "^")
}

