package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.domain.response_model

data class LinearSearchResponseModel<T>(
    val currentIndex: Int?,
    val searchEnded: Boolean,
    val isMatched: Boolean?,
    val currentElement: T?
)