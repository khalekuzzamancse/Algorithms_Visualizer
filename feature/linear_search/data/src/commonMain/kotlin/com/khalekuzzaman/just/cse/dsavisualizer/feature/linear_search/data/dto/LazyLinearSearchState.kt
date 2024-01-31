package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.dto

import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.domain.response_model.LinearSearchResponseModel

data class LazyLinearSearchState<T>(
    val currentIndex: Int?,
    val searchEnded: Boolean,
    val isMatched: Boolean? = null,
    val currentElement: T?,
){
    fun toModel()= LinearSearchResponseModel(
        currentIndex=currentIndex,
        searchEnded=searchEnded,
        isMatched=isMatched,
        currentElement=currentElement
    )
}