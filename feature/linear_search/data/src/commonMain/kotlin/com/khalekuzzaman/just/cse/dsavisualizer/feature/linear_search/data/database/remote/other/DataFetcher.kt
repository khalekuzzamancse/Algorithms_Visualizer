package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.database.remote.other

import com.khalekuzzaman.just.cse.dsavisualizer.core.database.mongodb.Filter
import com.khalekuzzaman.just.cse.dsavisualizer.core.database.mongodb.readDocument
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.database.remote.entities.TutorialTheoryEntity
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.dto.remote.TutorialTheoryDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class DataFetcher {
    fun fetchTutorialTheory(): Flow<TutorialTheoryDTO> {
        return readDocument<TutorialTheoryEntity>(
            collectionName = "tutorial_collection",
            filter = Filter("name", "LinearSearch")
        ).map { TutorialTheoryDTO(it.content ?: "") }
    }
}

fun main() {
    runBlocking {
        DataFetcher().fetchTutorialTheory()
    }
}