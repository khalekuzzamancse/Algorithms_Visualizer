package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.domain.response_model

data class PseudoCodeLineResponseModel(
    val line: String,
    val highLighting: Boolean = false,
    val lineNumber: Int,
)