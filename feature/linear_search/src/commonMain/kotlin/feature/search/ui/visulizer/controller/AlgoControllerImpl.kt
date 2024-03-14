package feature.search.ui.visulizer.controller

import feature.search.ui.visulizer.contract.AlgoStateController
import feature.search.ui.visulizer.contract.Pseudocode
import feature.search.ui.visulizer.contract.AlgoPseudocode
import feature.search.ui.visulizer.contract.AlgoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


internal class AlgoControllerImpl<T>(list: List<T>, private val target: T) : AlgoStateController<T> {
    private val builder=Sequences(list, target)
    private val iterator = builder.result.iterator()
    override val pseudocode=builder.pseudocode
    private val _state = MutableStateFlow(initializeState())
    override val algoState: StateFlow<AlgoState<T>> = _state.asStateFlow()


    override fun next() {
        if (iterator.hasNext()) {
            val res = iterator.next()

            _state.update { res }
        }

    }

    override fun hasNext() = iterator.hasNext()
    private fun initializeState(): AlgoState<T> =
        AlgoState(
            target = target,
            currentIndex = -1,
            searchEnded = false,
            isMatched = null,
            currentElement = null
        )


}

internal class Sequences<T>(
    private val list: List<T>,
    private val target: T,
) {
    private var currentIndex = -1 // Before search starts
    private var searchEnded = false
    private var isMatched: Boolean? = null
    private var currentElement: T? = null
    private val codeHighlighted = AlgoPseudocode()
    private val _pseudocode = MutableStateFlow(codeHighlighted.code)
     val pseudocode: StateFlow<List<Pseudocode.Line>> = _pseudocode.asStateFlow()

    val result = sequence {
        updatePseudocode(1)
        yield(newState())// Search not started
        for (i in list.indices) {
            currentIndex = i
            currentElement = list[i]
            isMatched = currentElement == target
            updatePseudocode(3)
            yield(newState())
            if (isMatched as Boolean) {
                searchEnded = true
                updatePseudocode(5)
                yield(newState()) // Target found
                break // Exit after finding the match
            }
            updatePseudocode(9)
            yield(newState())
        }

        if (!searchEnded) { // If the search ends without finding the target
            searchEnded = true
            updatePseudocode(12)
            yield(newState())
        }
    }

    private fun newState():AlgoState<T>{
      return  AlgoState(
          target = target,
          currentIndex = currentIndex,
          searchEnded = searchEnded,
          isMatched = isMatched,
          currentElement = currentElement
      )
    }
    private fun updatePseudocode(lineNo:Int){
        _pseudocode.update {
            codeHighlighted.highLightPseudocode(lineNo)
        }
    }

}




