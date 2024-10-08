package binarysearch.infrastructure

import binarysearch.domain.model.SimulationState


internal class Iterator<T : Comparable<T>>(private val array: List<T>, private val target: T) {
    private var low = 0
    private var high = array.size - 1

    fun start() = sequence {
        while (low <= high) {
            val mid = low + (high - low) / 2

            yield(SimulationState.PointerLow(index = low))  // Update the low pointer
            yield(SimulationState.PointerHigh(index = high))  // Update the high pointer
            yield(SimulationState.PointerMid(index = mid))  // Update the mid pointer

            when {
                array[mid] == target -> {
                    yield(SimulationState.FoundAt(index = mid))  // Found the target at mid
                    break
                }
                array[mid] < target -> {
                    low = mid + 1  // Search in the right half
                    yield(SimulationState.ClearMid)  // Mid pointer needs to be cleared when range changes
                }
                else -> {
                    high = mid - 1  // Search in the left half
                    yield(SimulationState.ClearMid)  // Mid pointer needs to be cleared when range changes
                }
            }
        }

        yield(SimulationState.Finished)  // Finish the search if target is not found
    }
}
