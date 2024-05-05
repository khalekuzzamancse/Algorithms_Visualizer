package selection_sort.di

import selection_sort.domain.AlgoPseudocode
import selection_sort.domain.AlgoSimulator
import selection_sort.infrastructure.AlgoSimulatorImpl

/**
 * Used the factory design pattern so provide the dependency ,also creating the complex object
 * for the client,
 * it manage the single source of truth for object creation and providing as  a the client code has
 * less coupling since the client does not direcly create the the dependency
 */

internal object Factory {

    fun <T:Comparable<T>> createAlgoSimulator(elements:List<T>): AlgoSimulator<T> {
       return AlgoSimulatorImpl(list = elements)
    }
    fun createAlgoPseudocode()= AlgoPseudocode()
}