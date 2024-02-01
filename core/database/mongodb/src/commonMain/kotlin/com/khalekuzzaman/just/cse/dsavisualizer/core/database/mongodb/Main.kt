package com.khalekuzzaman.just.cse.dsavisualizer.core.database.mongodb

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.count
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.time.LocalDateTime

private fun getDatabase(): MongoDatabase {
    val connectionString =
        "mongodb+srv://khalekuzzamancse:khalek_cse@dsavisualizercluster.t0zogph.mongodb.net/?retryWrites=true&w=majority"
    val client = MongoClient.create(connectionString = connectionString)
    val databaseName = "tutorial"
    return client.getDatabase(databaseName = databaseName)
}

suspend fun listAllCollection() {
    val database= getDatabase()
    print("Collection in this database are -----------> ")
    database.listCollectionNames().collect { print(" $it") }
   // readSpecificDocument(database)
    readCollection(database)

}
suspend fun readCollection(database: MongoDatabase) {
    val collection = database.getCollection<Tutorial>(collectionName = "tutorial_collection")
    collection.find<Tutorial>().collect {
        println(it)
    }
}
suspend fun readSpecificDocument(database: MongoDatabase) {
    val collection = database.getCollection<Restaurant>(collectionName = "restaurants")
    val queryParams = Filters
        .and(
            listOf(
                eq("cuisine", "American"),
                eq("borough", "Queens")
            )
        )


    collection
        .find<Restaurant>(queryParams)
        .limit(2)
        .collect {
            println(it)
        }

}
data class Tutorial(
    val content: String,
)
data class Restaurant(
    @BsonId
    val id: ObjectId,
    val address: Address,
    val borough: String,
    val cuisine: String,
    val grades: List<Grade>,
    val name: String,
    @BsonProperty("restaurant_id")
    val restaurantId: String
)

data class Address(
    val building: String,
    val street: String,
    val zipcode: String,
    val coord: List<Double>
)

data class Grade(
    val date: LocalDateTime,
    val grade: String,
    val score: Int
)