package bubble_sort.di

import bubble_sort.domain.AlgoPseudocode
import bubble_sort.domain.AlgoStateController
import bubble_sort.infrastructure.AlgoControllerImpl
import bubble_sort.infrastructure.BubbleSortIterator

/**
 * Used the factory design pattern so provide the dependency ,also creating the complex object
 * for the client,
 * it manage the single source of truth for object creation and providing as  a the client code has
 * less coupling since the client does not direcly create the the dependency
 */

internal object Factory {

    fun <T:Comparable<T>> createAlgoController(elements:List<T>): AlgoStateController<T> {
        val iterator = BubbleSortIterator(elements)
       return AlgoControllerImpl(iterator = iterator)
    }

}