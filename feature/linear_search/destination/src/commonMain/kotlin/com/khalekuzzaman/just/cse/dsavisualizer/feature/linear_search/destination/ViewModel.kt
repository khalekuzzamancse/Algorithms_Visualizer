package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.destination

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ViewModel {
    private val _selectedDestinationIndex = MutableStateFlow(-1)
    val selectedDestinationIndex= _selectedDestinationIndex.asStateFlow()
    val sections= listOf("Theory","Steps","Implementation","Visual")

    fun onDestinationSelected(index: Int) {
        _selectedDestinationIndex.value = index
    }
    fun onBackRequest(){
        _selectedDestinationIndex.update { -1 }
    }
}