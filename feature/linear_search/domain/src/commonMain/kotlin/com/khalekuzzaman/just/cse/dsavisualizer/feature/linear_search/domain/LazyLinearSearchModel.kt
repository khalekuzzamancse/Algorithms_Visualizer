package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.domain

data class LazyLinearSearchModel<T>(
    val currentIndex: Int?,
    val searchEnded: Boolean,
    val isMatched: Boolean?,
    val currentElement: T?
)