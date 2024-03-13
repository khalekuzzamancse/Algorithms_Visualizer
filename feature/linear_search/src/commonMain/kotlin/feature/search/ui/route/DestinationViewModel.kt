package feature.search.ui.route

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class DestinationViewModel {
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