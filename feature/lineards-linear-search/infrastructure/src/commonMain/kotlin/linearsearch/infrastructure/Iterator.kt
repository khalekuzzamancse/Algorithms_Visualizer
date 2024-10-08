package linearsearch.infrastructure

import linearsearch.domain.model.SimulationState


internal class Iterator<T : Comparable<T>>(private val array: List<T>, private val target: T) {
    private val length = array.size

    fun start() = sequence {
        for (i in 0 until length) {
            yield(SimulationState.PointerI(index = i)) // Update the pointer i

            if (array[i] == target) { // Check if the current element matches the target
                yield(SimulationState.FoundAt(index = i)) // Found the target at index i
                break // Exit the loop once the target is found
            }
        }
        yield(SimulationState.Finished) // If the target is not found, mark search as finished
    }
}
