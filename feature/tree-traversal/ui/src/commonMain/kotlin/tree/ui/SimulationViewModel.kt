@file:Suppress("functionName")

package tree.ui

import androidx.compose.ui.graphics.Color
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
    lateinit var graphController: GraphViewerController
    private lateinit var simulator: Simulator

    private val _isInputMode = MutableStateFlow(true)
    val isInputMode = _isInputMode.asStateFlow()

    fun onGraphCreated(result: TreeResult) {
        graphController = result.controller
        simulator = DiContainer.createSimulator(
            root = mapToSimpleTreeNode(result.root),
            type = TraversalType.POST_ORDER
        )

        _isInputMode.update { false }

    }

    fun onNext() {
        when (val state = simulator.next()) {
            is SimulationState.ProcessingNode -> handleProcessingNode(state.node)
            SimulationState.Finished -> handleSimulationFinished()
            else -> Unit // No action for other states
        }
    }


    private fun handleProcessingNode(node: TreeNode) {
        graphController.changeNodeColor(id = node.id, color = Color.Blue)
    }


    private fun handleSimulationFinished() {

    }


    fun mapToSimpleTreeNode(node: graph.tree.TreeNode): TreeNode {
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