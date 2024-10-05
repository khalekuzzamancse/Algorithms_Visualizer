package graphtopologicalsort.presentationlogic.controller

import graph.graph.common.model.GraphResult
import graph.graph.viewer.controller.GraphViewerController
import graphtopologicalsort.domain.model.ColorModel
import graphtopologicalsort.domain.model.SimulationState
import graphtopologicalsort.presentationlogic.model.NodeModel
import kotlinx.coroutines.flow.StateFlow
/**
 * This layer should not depend on on any libraries or UI implementations such as GraphViewController
 * that is why exposing some event from here
 */
interface Controller {


    interface AutoPlayer {
        fun isAutoPlayMode(): Boolean
        fun autoPlayRequest(delay: Int)

        /**Must dismiss it if no longer required because it has coroutines run,we have to stop it*/
        fun dismiss()
        var onNextCallback: () -> Unit
    }

    interface NeighborSelector {
        /** Pair(node id, label),Selected will be passed via this callback**/
        val neighbors: StateFlow<Set<NodeModel>>
        fun onSelectionRequest(nodes: Set<NodeModel>, callback: (List<String>) -> Unit)
        fun onSelected(id: List<String>)
    }




}