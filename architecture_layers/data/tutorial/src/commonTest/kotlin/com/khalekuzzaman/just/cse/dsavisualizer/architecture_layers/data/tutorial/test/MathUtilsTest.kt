package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.test

import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial.addNumbers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MathUtilsTest {

    @Test
    fun testAddNumbers() {
        assertEquals(4, addNumbers(2, 2))
    }
}