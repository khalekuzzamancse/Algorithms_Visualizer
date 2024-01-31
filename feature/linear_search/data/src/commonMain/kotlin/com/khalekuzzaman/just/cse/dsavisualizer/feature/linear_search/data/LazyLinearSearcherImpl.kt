package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data

import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.dto.LazyLinearSearchState
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.dto.LinearSearchPseudocode
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.domain.LinearSearcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LazyLinearSearcherImpl<T>(
    private val list: List<T>,
    private val target: T,
) : LinearSearcher<T> {
    private val codeHighlighted = LinearSearchPseudocode()
    private val _codes = MutableStateFlow(codeHighlighted.code.map { it.toModel() })

    //
    override val pseudocode = _codes.asStateFlow()

    //
    private val currentIndex = MutableStateFlow<Int?>(null)
    private val searchEnded = MutableStateFlow(false)
    private val isMatched = MutableStateFlow<Boolean?>(null)
    private val currentElement = MutableStateFlow<T?>(null)
    private val searchNotStarted = MutableStateFlow(true)
    private val _state = MutableStateFlow(
        LazyLinearSearchState(
            currentIndex.value,
            searchEnded.value,
            isMatched.value,
            currentElement.value
        ).toModel()
    )
    override val state = _state.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            combine(
                currentIndex,
                searchEnded,
                isMatched,
                currentElement
            ) { index, ended, matched, element ->
                LazyLinearSearchState(index, ended, matched, element)
            }.collect { state ->
                _state.update { state.toModel() }
            }
        }

    }


    init {
        CoroutineScope(Dispatchers.Default).launch {
            currentIndex.collect { currentIndex ->
                if (currentIndex != null && currentIndex < list.size)
                    currentElement.update { list[currentIndex] }
            }
        }
    }

    override fun next() {
        if (searchNotStarted.value) {
            beReadyToSearch()
            return
        }
        if (searchEnded.value) {
            return
        }
        onSearchStartedButNotEnded()

    }


    private fun onSearchStartedButNotEnded() {
        currentIndex.value?.let { currentIndex ->
            //4 WHILE (index < number of items in the list)
            highLightCode(4)
            if (currentIndex < list.size) {
                isMatched.update { currentElement.value == target }
                highLightCode(listOf(4, 5))
                //IF (list[index] == target element)
                if (isMatched.value as Boolean) {
                    //7 RETURN index
                    highLightCode(listOf(4, 5, 7))

                    onSearchEnded()
                } else {
                    //9 INCREMENT index by 1
                    highLightCode(listOf(4, 5, 9))
                    readyForNextIterator()
                }
            } else {
                //11 RETURN -1
                highLightCode(listOf(4, 5, 11))
                onSearchEnded()
            }
        }
    }

    private fun readyForNextIterator() {
        currentIndex.update { it?.plus(1) }
        isMatched.update { null }
    }

    override fun hasNext() = !searchEnded.value


    private fun onSearchEnded() {
        searchEnded.update { true }
        isMatched.update { null }
        currentIndex.update { null }
        currentElement.update { null }
    }

    private fun beReadyToSearch() {
        highLightCode(3) //initialize index=0
        currentElement.update { list[0] }
        currentIndex.update { 0 }
        searchNotStarted.update { false }
    }

    private fun highLightCode(line: List<Int>) {
        CoroutineScope(Dispatchers.Default).launch {
            line.forEach { lineNo ->
                _codes.update {
                    codeHighlighted.highLightPseudocode(lineNo).map { it.toModel() }
                }
                delay(200)
            }

        }

    }

    private fun highLightCode(line: Int) {
        _codes.update {
            codeHighlighted.highLightPseudocode(line).map { it.toModel() }
        }


    }

}


