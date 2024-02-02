package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial

import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.implementation.Implementations
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.pseudocode.Pseudocode
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.steps.Steps
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.theory.Theory
import com.khalekuzzaman.just.cse.dsavisualizer.core.database.mongodb.Filter
import com.khalekuzzaman.just.cse.dsavisualizer.core.database.mongodb.MongoCRUD
import kotlinx.coroutines.flow.Flow

class TutorialCRUD {
    private val implementation = "implementation_collection"
    private val steps = "steps_collection"
    private val pseudocode = "pseudocode_collection"
    private val theory = "theory_collection"
    private val fieldName="algoName"
    private val mongoCRUD = MongoCRUD()

    /**
     * method is for admin to add ,remove or update something
     */
    internal suspend fun addImplementation(data: Implementations) {
        val success = mongoCRUD.insert(collectionName = implementation, document = data)
//        println("TutorialCRUD:addImplementation() :: $success")
    }

    internal suspend fun addStep(data: Steps) {
        val success = mongoCRUD.insert(collectionName = steps, document = data)
        println("TutorialCRUD:addStep() :: $success")
    }

    internal suspend fun addPseudocode(data: Pseudocode) {
        val success = mongoCRUD.insert(collectionName = pseudocode, document = data)
        println("TutorialCRUD:addPseudocode() :: $success")
    }
    internal suspend fun addTheory(data: Theory) {
        val success = mongoCRUD.insert(collectionName = theory, document = data)
        println("TutorialCRUD:addTheory() :: $success")
    }


    fun getImplementation(algoName: AlgoName): Flow<Implementations> =
        mongoCRUD.readDocument<Implementations>(
            collectionName = implementation,
            filter = Filter(fieldName, algoName.toString())
        )
    fun getTheory(algoName: AlgoName)=
        mongoCRUD.readDocument<Theory>(
            collectionName = theory,
            filter = Filter(fieldName, algoName.toString())
        )
    fun getSteps(algoName: AlgoName)=
        mongoCRUD.readDocument<Steps>(
            collectionName = steps,
            filter = Filter(fieldName, algoName.toString())
        )
    fun getPseudocode(algoName: AlgoName)=
        mongoCRUD.readDocument<Pseudocode>(
            collectionName = pseudocode,
            filter = Filter(fieldName, algoName.toString())
        )

}