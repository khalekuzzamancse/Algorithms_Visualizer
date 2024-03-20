package binary_search.infrastructure

import binary_search.PackageLevelAccess
import binary_search.domain.BaseIterator

@PackageLevelAccess //avoid to access other layer such ui layer
class BinarySearchIterator<T : Comparable<T>>(
    private val elements: List<T>,
    target: T,
) : BaseIterator<T>(elements.size, target) {
    private var ended = false
    private var isFound: Boolean? = null
   override val result = sequence {
       updateVariablesState(length = length,target=target,low=low,high=high)
        yield(newState()) // Initial state
        while (low <= high) {
             mid = (low + high) / 2
            midValue = elements[mid!!]
            isFound = midValue == target
            updateVariablesState(length = length,target=target,low=low,high=high,mid=mid,midValue=midValue)
            yield(newState(mid))
            if (isFound as Boolean) {
                ended = true
                yield(newState(mid)) // Target found
                break
            } else if (midValue!! < target) {
                low = mid!! + 1
                updateVariablesState(length = length,target=target,low=low,high=high)
                yield(newState())
            } else {
                high = mid!! - 1
                updateVariablesState(length = length,target=target,low=low,high=high)
                yield(newState())
            }
            yield(newState())
        }
       //return -1 ;should not use return in that statement here...
        if (!ended) { // If the search ends without finding the target
            ended = true
            yield(createEndState())
        }
       updateVariablesState()//clear the pseudocode variable state
       yield(createEndState())//return the end state for both successful search or un successful search
    }



}

