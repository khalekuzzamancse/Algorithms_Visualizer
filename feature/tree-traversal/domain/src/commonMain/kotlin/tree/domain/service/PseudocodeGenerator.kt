package tree.domain.service

import tree.domain.model.BFSCodeStateModel
import tree.domain.model.CodeStateModel
import tree.domain.model.DFSCodeStateModel
import tree.domain.model.TraversalType

object PseudocodeGenerator {
    fun generate(type: TraversalType, model: CodeStateModel) = when (type) {
        TraversalType.PRE_ORDER -> preOrder(model as DFSCodeStateModel)
        TraversalType.POST_ORDER -> postOrderCode(model as DFSCodeStateModel)
        TraversalType.BFS-> bsf(model as BFSCodeStateModel)
        else-> ""
    }

}

private  fun postOrderCode(state:DFSCodeStateModel)= with(state){
    """
            postOrder(root) {
                stack = Stack<TreeNode>() ${stack.showState()}
                visited = List<TreeNode> ${visited.showState()}
                stack.push(root)
                
                while (stack.isNotEmpty) {${stackIsNotEmpty.showState()}
                    current = stack.peek() ${current.showState()}
                    isVisited = isTheNodeIsVisited(node)
                    if (isVisited) ${isVisited.showState()}
                           stack.pop()
                    else{
                         visited.add(current)
                         pushAllChildInReverseOrder(current) ${reverseOrderChildren.showState()}
                    }
                      
                }
            }
""".trimIndent()
}

private  fun preOrder(state:DFSCodeStateModel)= with(state){
    """
            preOrder(root) {
                stack = Stack<TreeNode>() ${stack.showState()}
                visited = List<TreeNode> ${visited.showState()}
                stack.push(root)
                
                while (stack.isNotEmpty) {${stackIsNotEmpty.showState()}
                    current = stack.peek() ${current.showState()}
                    isNotVisited = !(isTheNodeIsVisited(current))
                    if (isNotVisited) ${isNotVisited.showState()}
                        visited.add(current)
                    stack.pop()
                    pushAllChildInReverseOrder(current) ${reverseOrderChildren.showState()}
                }
            }
""".trimIndent()
}
private  fun bsf(state:BFSCodeStateModel)= with(state){
    """
     bfs(root) {
         queue: Queue<TreeNode> ${queue.showState()}
         visited :List<TreeNode> ${visited.showState()}

         queue.add(root)
         visited.add(root)

         while (queue.isNotEmpty) { ${queueIsNotEmpty.showState()}
             val current = queue.poll()

             current.children.forEach { child ->
                 isVisited=isChildVisited(child)
                 if (isVisited) {  ${isNotVisited.showState()}
                     visited.add(child)
                     queue.add(child)
                 }
             }
         }

     }
""".trimIndent()
}
private fun String?.showState() = this?.let { "//$it" } ?: ""

