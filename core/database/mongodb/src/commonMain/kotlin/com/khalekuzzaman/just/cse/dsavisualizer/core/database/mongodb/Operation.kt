package com.khalekuzzaman.just.cse.dsavisualizer.core.database.mongodb

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.Flow

fun getDatabase(): MongoDatabase {
    val connectionString =
        "mongodb+srv://khalekuzzamancse:khalek_cse@dsavisualizercluster.t0zogph.mongodb.net/?retryWrites=true&w=majority"
    val client = MongoClient.create(connectionString = connectionString)
    val databaseName = "tutorial"
    return client.getDatabase(databaseName = databaseName)
}



data class Filter(
    val field: String,
    val value: String
)

inline fun <reified T : Any> readDocument(
    collectionName: String,
    filter: Filter,
): Flow<T> {
    val collection = getDatabase().getCollection<T>(collectionName = collectionName)
    val queryParams = Filters
        .and(
            listOf(
                eq(filter.field, filter.value),
            )
        )
    return collection
         .find<T>(queryParams)
         .limit(1)

}



