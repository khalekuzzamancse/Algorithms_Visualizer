package lineards.binary_search.infrastructure

import lineards.binary_search.domain.model.CodeStateModel
import lineards.binary_search.domain.model.SimulationState
import lineards.binary_search.domain.service.PseudocodeGenerator


internal class Iterator<T : Comparable<T>>(private val array: List<T>, private val target: T) {
    private var low = 0
    private var high = array.size - 1
    private val OutOfScope = null

    private var model = CodeStateModel(target = "$target")

    private fun CodeStateModel.toCode() = PseudocodeGenerator.generate(this)

    fun start() = sequence {
        yield(SimulationState.Start(model.toCode()))

        while (low <= high) {
            val mid = low + (high - low) / 2
            model = model.copy(low = low)

            yield(SimulationState.PointerLow(index = low, code = model.toCode()))
            model = model.copy(high = high)

            yield(SimulationState.PointerHigh(index = high, code = model.toCode()))

            val current = array[mid]

            model = model.copy(mid = mid, current = "$current")

            yield(
                SimulationState.PointerMid(
                    index = mid,
                    model.toCode()
                )
            )  // Update the mid pointer

            when {
                current == target -> {
                    model = model.copy(isFound = "true; ", returnIndex = mid)
                    yield(
                        SimulationState.FoundAt(
                            index = mid,
                            code = model.toCode(),
                        )
                    )  // Found the target at mid
                    break
                }

                current < target -> {
                    low = mid + 1  // Search in the right half
                    model = model.copy(isFound = "false", currentLessThanTarget = "true ; $current<$target")
                    yield(SimulationState.ClearMid(model.toCode()))  // Mid pointer needs to be cleared when range changes
                }

                else -> {
                    high = mid - 1  // Search in the left half
                    model = model.copy(isFound = "false", currentGreaterThanTarget = "$current>$target")
                    yield(SimulationState.ClearMid(model.toCode()))  // Mid pointer needs to be cleared when range changes
                }
            }
            //Clear old state
            model = model.copy(mid = OutOfScope, current = OutOfScope, isFound = OutOfScope, currentGreaterThanTarget = OutOfScope, currentLessThanTarget = OutOfScope)

        }
        model = model.copy( mid = OutOfScope, high = OutOfScope, low = OutOfScope, isFound = OutOfScope, currentGreaterThanTarget = OutOfScope, currentLessThanTarget = OutOfScope)
        yield(SimulationState.Finished(model.toCode()))  // Finish the search if target is not found
    }
}
