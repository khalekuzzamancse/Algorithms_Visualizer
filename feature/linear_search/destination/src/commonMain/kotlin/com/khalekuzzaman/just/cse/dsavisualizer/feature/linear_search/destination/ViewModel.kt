package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.destination

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModel {
    private val _selectedDestinationIndex = MutableStateFlow(0)
    val selectedDestinationIndex= _selectedDestinationIndex.asStateFlow()
    val sections= listOf("Theory","Steps","Implementation","Visual")

    fun onDestinationSelected(index: Int) {
        _selectedDestinationIndex.value = index
    }
}