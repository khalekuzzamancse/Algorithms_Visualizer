package com.khalekuzzaman.just.cse.dsavisualizer.core.database.mongodb

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.Flow

class MongoCRUD {
    val database = getDatabase()
    suspend inline fun <reified T : Any> insert(collectionName: String, document: T): Boolean {
        val collection = database.getCollection<T>(collectionName = collectionName)
        collection.insertOne(document).also {
            return it.insertedId != null
        }
    }
    inline fun <reified T : Any> readDocument(
        collectionName: String,
        filter: Filter,
    ): Flow<T> {
        val collection = getDatabase().getCollection<T>(collectionName = collectionName)
        val queryParams = Filters
            .and(
                listOf(
                    Filters.eq(filter.field, filter.value),
                )
            )
        return collection
            .find<T>(queryParams)
            .limit(1)

    }


}
