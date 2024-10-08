package feature.search.infrastructure

import feature.search.PackageLevelAccess
import feature.search.domain.BaseIterator
@PackageLevelAccess //avoid to access other layer such ui layer
class LinearSearchIterator<T : Comparable<T>>(
    private val elements: List<T>,
    target: T,
) : BaseIterator<T>(elements.size, target) {

    override val pseudocode = super.pseudocode
    override val result = sequence {
        yield(newState())// Search not started
        updateVariablesState(length = length)
        for (i in elements.indices) {
            index = i
            updatePseudocode(5)
            updateVariablesState(length = length, target = target, index = index)
            yield(newState())
            current = elements[i]
            isFound = current == target

            updateVariablesState(length = length, target = target, index = index, current = current)
            if (isFound as Boolean) {
                searchEnded = true
                yield(newState()) // Target found
                updatePseudocode(10)
                break // Exit after finding the match
            }
            yield(newState())
            updateVariablesState(length = length, target = target)
            updatePseudocode(12)

        }

        if (!searchEnded) { // If the search ends without finding the target
            searchEnded = true
            index = -1
            updateVariablesState()
            updatePseudocode(14)
        }
        yield(createEndState())
    }


}

