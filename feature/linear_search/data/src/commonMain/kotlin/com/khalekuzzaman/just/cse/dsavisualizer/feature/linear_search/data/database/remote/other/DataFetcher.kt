package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.database.remote.other

import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.AlgoName
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.TutorialCRUD
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.dto.remote.TutorialPseudocodeDTO
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.dto.remote.TutorialStepDTO
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.dto.remote.TutorialTheoryDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataFetcher {
    fun fetchTutorialTheory(): Flow<TutorialTheoryDTO> {
        return TutorialCRUD().getTheory(
            algoName = AlgoName.LinearSearch
        ).map { TutorialTheoryDTO(it.content) }
    }
    fun fetchSteps(): Flow<TutorialStepDTO> {
        return TutorialCRUD().getSteps(
            algoName = AlgoName.LinearSearch
        ).map { TutorialStepDTO(it.steps) }
    }
    fun fetchPseudoCode(): Flow<TutorialPseudocodeDTO> {
        return TutorialCRUD().getPseudocode(
            algoName = AlgoName.LinearSearch
        ).map { TutorialPseudocodeDTO(it.code) }
    }
//    fun fetchImplementation(): Flow<TutorialPseudocodeDTO> {
//        return TutorialCRUD().getImplementation(
//            algoName = AlgoName.LinearSearch
//        ).map { TutorialPseudocodeDTO(it.code) }
//    }
}
