package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial

import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.implementation.Implementation
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.implementation.Implementations
import kotlinx.coroutines.runBlocking

fun main() {
    val implementations=Implementations(
        algorithmName = "Linear Search",
        implementations = listOf(
            Implementation(
                languageName = "C++",
                code = "cout<<hello<<endl;"
            )
        )
    )
    runBlocking {
        TutorialCRUD().addImplementation(implementations)
    }

}