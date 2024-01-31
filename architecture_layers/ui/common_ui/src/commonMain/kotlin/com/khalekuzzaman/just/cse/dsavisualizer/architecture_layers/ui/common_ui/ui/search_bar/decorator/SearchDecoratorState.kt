package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.search_bar.decorator

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchDecoratorState<T>(
    private val items: List<T>,
    val predicate: (T, String) -> Boolean,
) {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    private val _results = MutableStateFlow(items)
    val results = _results.asStateFlow()
    fun onQueryChanged(query: String) {
        _query.update { query }
        _results.value = results.value.filter { predicate(it, query) }
        if (query == "") {
            _results.value = items
        }
    }

    private val _active = MutableStateFlow(true)
    val active = _active.asStateFlow()
    fun onActiveChanged(status: Boolean) {
        _active.value = status

    }

    private val _showSearch = MutableStateFlow(false)
    fun onSearchChanged(status: Boolean) {
        _showSearch.value = status
        if (!status) {
            clear()
        }
    }

    private fun clear() {
        _results.value = items
        _query.value = ""

    }

}
