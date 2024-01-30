package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data

data class LazyLinearSearchState<T>(
    val currentIndex: Int?,
    val searchEnded: Boolean,
    val isMatched: Boolean? = null,
    val currentElement: T?,
)