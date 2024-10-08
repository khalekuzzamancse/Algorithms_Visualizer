package binary_search.di

import binary_search.PackageLevelAccess
import binary_search.domain.AlgoStateController
import binary_search.infrastructure.AlgoControllerImpl
import binary_search.infrastructure.BinarySearchIterator

/**
 * Used the factory design pattern so provide the dependency ,also creating the complex object
 * for the client,
 * it manage the single source of truth for object creation and providing as  a the client code has
 * less coupling since the client does not direcly create the the dependency
 */
@OptIn(PackageLevelAccess::class)//okay to use in data/infrastructure layer Layer
internal object Factory {
    fun <T:Comparable<T>> createAlgoController(elements:List<T>, target:T): AlgoStateController<T> {
        val iterator = BinarySearchIterator(elements, target)
       return AlgoControllerImpl(iterator = iterator)
    }
}