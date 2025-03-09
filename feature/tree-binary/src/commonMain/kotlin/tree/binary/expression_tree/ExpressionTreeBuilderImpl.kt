package tree.binary.expression_tree

import tree.binary.tree_view.Node


interface ExpressionTreeBuilder {
    fun buildTree(expression: String): Node<String>?

    companion object{
        fun   create():ExpressionTreeBuilder=ExpressionTreeBuilderImpl()
    }
}






private class ExpressionTreeBuilderImpl:ExpressionTreeBuilder {

    override fun buildTree(expression: String): Node<String>? {
        val tokens = tokenize(expression)
        val postfix = infixToPostfix(tokens)
        return buildExpressionTree(postfix)
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

    private fun buildExpressionTree(postfix: List<String>): Node<String>? {
        val stack = ArrayDeque<Node<String>>()
        for (token in postfix) {
            if (isOperator(token)) {
                val right = stack.removeLastOrNull() ?: return null
                val left = stack.removeLastOrNull() ?: return null
                stack.addLast(Node(token, left, right))
            } else {
                stack.addLast(Node(token))
            }
        }
        return stack.lastOrNull()
    }

    private fun isOperator(token: String): Boolean = token in setOf("+", "-", "*", "/", "^")
}

