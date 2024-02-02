package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.repoisitory

import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.database.remote.other.DataFetcher
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.domain.repoisitory.TutorialRepository
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.domain.response_model.TutorialTheoryResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TutorialRepositoryImpl : TutorialRepository {
    override fun getTutorialTheory(): Flow<TutorialTheoryResponseModel> {
        return DataFetcher().fetchTutorialTheory().map {
            TutorialTheoryResponseModel(it.richString)
        }
    }
}