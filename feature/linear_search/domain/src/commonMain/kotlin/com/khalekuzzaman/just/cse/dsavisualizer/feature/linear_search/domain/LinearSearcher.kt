package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.domain

import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.domain.response_model.LinearSearchResponseModel
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.domain.response_model.PseudoCodeLineResponseModel
import kotlinx.coroutines.flow.StateFlow

interface LinearSearcher<T> {
    val pseudocode: StateFlow<List<PseudoCodeLineResponseModel>>
    val state: StateFlow<LinearSearchResponseModel<T>>
    fun next()
    fun hasNext(): Boolean
}