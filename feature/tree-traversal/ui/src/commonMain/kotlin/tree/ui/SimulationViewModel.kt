@file:Suppress("functionName")
package tree.ui
import androidx.compose.ui.graphics.Color
import core.commonui.controller.ControllerFactory
import graph.graph.viewer.controller.GraphViewerController
import graph.tree.TreeResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tree.di.DiContainer
import tree.domain.model.SimulationState
import tree.domain.model.TraversalType
import tree.domain.model.TreeNode
import tree.domain.service.Simulator
class SimulationViewModel {
    private val tag =this.javaClass.simpleName
    lateinit var graphController: GraphViewerController
    private lateinit var simulator: Simulator
    private val _traversalType=MutableStateFlow<TraversalType?>(null)
    val traversalType=_traversalType.asStateFlow()
    private  lateinit var  result: TreeResult
    val autoPlayer = ControllerFactory.createAutoPlayer(::onNext)


    private val _isInputMode = MutableStateFlow(true)
    val isInputMode = _isInputMode.asStateFlow()
    private val _code = MutableStateFlow<String?>(null)
    val code = _code.asStateFlow()

    fun onGraphCreated(result: TreeResult) {
        this.result=result
        _isInputMode.update { false }
        graphController = result.controller
    }
    fun selectTraversalType(type:TraversalType){
        _traversalType.update { type }
         simulator=  DiContainer.createSimulator(
                root = mapToSimpleTreeNode(result.root),
                type = type
            )
    }
    fun onNext() {
        val state = simulator.next()
        _code.update { state.code }
        when (state) {
            is SimulationState.ProcessingNode -> handleProcessingNode(state.node)
            is SimulationState.Finished -> handleSimulationFinished()
            else -> Unit // No action for other states
        }
    }
    fun reset(){
        graphController.reset()
        autoPlayer.dismiss()
        simulator=  DiContainer.createSimulator(
            root = mapToSimpleTreeNode(result.root),
            type = traversalType.value!!
        )

    }

    private fun handleProcessingNode(node: TreeNode) {
        graphController.changeNodeColor(id = node.id, color = Color.Blue)
    }


    private fun handleSimulationFinished() {

    }


    private fun mapToSimpleTreeNode(node: graph.tree.TreeNode): TreeNode {
        val simpleNode = TreeNode(node.node.id)

        // Recursively map children
        node.children.forEach { child ->
            simpleNode.children.add(mapToSimpleTreeNode(child))
        }

        return simpleNode
    }


    private fun graph.tree.TreeNode._toNodeModel() = TreeNode(
        id = this.node.id,
    )


}