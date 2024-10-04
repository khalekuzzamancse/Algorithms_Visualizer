package graphtraversal.presentationlogic.controller

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NeighborSelectorImpl : Controller.NeighborSelector {
    private val _neighbors = MutableStateFlow<Set<Pair<String, String>>>(emptySet())
    private var callback: (String) -> Unit = {}

    override val neighbors = _neighbors.asStateFlow()

    override fun onSelectionRequest(nodes: Set<Pair<String, String>>, callback: (String) -> Unit) {
        _neighbors.update { nodes }
        this.callback = callback
    }

    override fun onSelected(id: String) {
        callback(id)
        _neighbors.update { emptySet() }
    }
}