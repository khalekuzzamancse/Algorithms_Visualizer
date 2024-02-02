package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.other.viewmodel

import androidx.compose.ui.graphics.Color
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.LazyLinearSearcherImpl
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LinearSearchViewModel<T : Any>(
    private val list: List<T>,
    private val visitedCellColor: Color,
    private val cellSizePx: Float,
    target:T,
) {
    val arrayManager = ArrayManager(list = list, cellSizePx = cellSizePx)
    val searcher = LazyLinearSearcherImpl(list = list, target =target)
    private val _pointerIndex = MutableStateFlow<Int?>(null)
    val pointerIndex =_pointerIndex.asStateFlow()
    val pseudocode=searcher.pseudocode
    private val _showPseudocode=MutableStateFlow(false)
    val showPseudocode=_showPseudocode.asStateFlow()
    fun togglePseudocodeVisibility(){
        _showPseudocode.update { !it }
    }
    init {
        CoroutineScope(Dispatchers.Default).launch {
            searcher.state.collect{state->
                state.currentIndex?.let { index ->
                    if (index>=0&&index<list.size){
                        _pointerIndex.update { index }
                        arrayManager.changeCellColor(
                            index=index,
                            color = visitedCellColor
                        )

                    }
                    else{
                        _pointerIndex.update { null}
                    }
                }

            }
        }
    }




}