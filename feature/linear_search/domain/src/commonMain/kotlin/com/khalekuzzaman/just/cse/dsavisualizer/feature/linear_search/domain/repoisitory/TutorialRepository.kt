package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.domain.repoisitory

import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.domain.response_model.TutorialTheoryResponseModel
import kotlinx.coroutines.flow.Flow

interface TutorialRepository {
    fun getTutorialTheory(): Flow<TutorialTheoryResponseModel>
}