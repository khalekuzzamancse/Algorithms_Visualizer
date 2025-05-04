package lineards.linear_search.infrastructure

import lineards.linear_search.domain.model.CodeStateModel
import lineards.linear_search.domain.model.SimulationState
import lineards.linear_search.domain.service.PseudocodeGenerator


internal class Iterator<T : Comparable<T>>(
    private val array: List<T>, private val target: T
) {

    private val length = array.size
    private var model = CodeStateModel(len = length, target = "$target")

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
