package graph._core.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NeighborSelectorImpl : Controller.NeighborSelector {
    private val _neighbors = MutableStateFlow<Set<NodeModel>>(emptySet())
    private var callback: (List<String>) -> Unit = {}

    override val neighbors = _neighbors.asStateFlow()

    override fun onSelectionRequest(nodes: Set<NodeModel>, callback: (List<String>) -> Unit) {
        _neighbors.update { nodes }
        this.callback = callback
    }

    override fun onSelected(id: List<String>) {
        callback(id)
        _neighbors.update { emptySet() }
    }
}