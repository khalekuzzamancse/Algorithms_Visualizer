package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.dto.non_database

import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.domain.response_model.PseudoCodeLineResponseModel

data class PseudoCodeLine(
    val line: String,
    val lineNumber:Int,
    val highLighting:Boolean=false,
){
    fun toModel()=PseudoCodeLineResponseModel(
        line = line,
        lineNumber = lineNumber,
        highLighting = highLighting
    )
}