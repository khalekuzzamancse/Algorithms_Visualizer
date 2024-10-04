package graphtraversal.presentationlogic.controller

import kotlinx.coroutines.flow.StateFlow

interface Controller {
    val autoPlayer: AutoPlayer
    val neighborSelector: NeighborSelector

    interface AutoPlayer  {
        fun isAutoPlayMode(): Boolean
        fun autoPlayRequest(delay: Int)

        /**Must dismiss it if no longer required because it has coroutines run,we have to stop it*/
        fun dismiss()
        var onNextCallback: () -> Unit
    }

    interface NeighborSelector {
        /** Pair(node id, label),Selected will be passed via this callback**/
        val neighbors: StateFlow<Set<Pair<String, String>>>
        fun onSelectionRequest(nodes: Set<Pair<String, String>>, callback: (String) -> Unit)
        fun onSelected(id:String)
    }
}