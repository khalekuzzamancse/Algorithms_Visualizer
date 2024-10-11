package linearsearch.infrastructure

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import linearsearch.domain.model.PseudocodeModel
import linearsearch.domain.model.SimulationState
import linearsearch.domain.service.PseudocodeGenerator


internal class Iterator<T : Comparable<T>>(
    private val array: List<T>, private val target: T
) {

    private val length = array.size
    private var model = PseudocodeModel(len = length, target = "$target")

    private fun generateCode() = PseudocodeGenerator.generate(model)
    private var isFound:Boolean=false
    //deal mean out of scope
    private val DEAD=null

    fun start() = sequence {
        yield(SimulationState.Start(PseudocodeGenerator.rawCode))

        for (i in 0 until length) {
            val current = array[i]
             isFound = (current == target)

            model = model.copy(i = i, current = "$current", isFound = isFound)

            yield(
                SimulationState.PointerI(
                    index = i,
                    code = generateCode()
                )
            ) // Update the pointer i

            if (isFound) {
                model=model.copy(returnIndex = i,i = DEAD, current = DEAD, isFound = DEAD)
                yield(
                    SimulationState.FoundAt(
                        index = i,
                        code = generateCode()
                    )
                ) // Found the target at index i

                break //same as return...
            }
        }
        if (!isFound)
        model = model.copy(returnIndex = -1, i = DEAD, current = DEAD, isFound = DEAD)
        yield(SimulationState.Finished(generateCode())) // If the target is not found, mark search as finished
    }
}
