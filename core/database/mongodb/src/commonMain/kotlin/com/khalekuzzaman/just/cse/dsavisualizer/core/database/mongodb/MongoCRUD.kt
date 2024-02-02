package com.khalekuzzaman.just.cse.dsavisualizer.core.database.mongodb

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase

class MongoCRUD {
    val database = getDatabase()
    suspend inline fun <reified T : Any> insert(collectionName: String, document: T): Boolean {
        val collection = database.getCollection<T>(collectionName = collectionName)
        collection.insertOne(document).also {
            return it.insertedId != null
        }
    }

    private fun getDatabase(): MongoDatabase {
        val connectionString =
            "mongodb+srv://khalekuzzamancse:khalek_cse@dsavisualizercluster.t0zogph.mongodb.net/?retryWrites=true&w=majority"
        val client = MongoClient.create(connectionString = connectionString)
        val databaseName = "tutorial"
        return client.getDatabase(databaseName = databaseName)
    }

}