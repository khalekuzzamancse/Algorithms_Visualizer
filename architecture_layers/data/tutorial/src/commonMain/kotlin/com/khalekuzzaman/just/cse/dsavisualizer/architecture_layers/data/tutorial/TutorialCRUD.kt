package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial

import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.implementation.Implementations
import com.khalekuzzaman.just.cse.dsavisualizer.core.database.mongodb.MongoCRUD

class TutorialCRUD {
    private val collection="implementation_collection"
    suspend fun addImplementation(data:Implementations){
       val success= MongoCRUD().insert(collectionName = collection, document =data )
        println("TutorialCRUD:addImplementation() :: $success")
    }

}