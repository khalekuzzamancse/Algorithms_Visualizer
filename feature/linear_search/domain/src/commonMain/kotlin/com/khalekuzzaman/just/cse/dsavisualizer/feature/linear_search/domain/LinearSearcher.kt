package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.domain

import kotlinx.coroutines.flow.StateFlow

interface LinearSearcher<T> {
    val pseudocode: StateFlow<PseudoCodeLineModel>
    val state: StateFlow<LazyLinearSearchModel<T>>

    fun next()
    fun hasNext(): Boolean
}