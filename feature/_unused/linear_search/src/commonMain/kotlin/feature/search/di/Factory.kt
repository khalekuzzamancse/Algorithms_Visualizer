package feature.search.di

import feature.search.PackageLevelAccess
import feature.search.domain.AlgoStateController
import feature.search.infrastructure.AlgoControllerImpl
import feature.search.infrastructure.LinearSearchIterator

/**
 * Used the factory design pattern so provide the dependency ,also creating the complex object
 * for the client,
 * it manage the single source of truth for object creation and providing as  a the client code has
 * less coupling since the client does not direcly create the the dependency
 */
@OptIn(PackageLevelAccess::class)//okay to use in data/infrastructure layer Layer
internal object Factory {

    fun <T:Comparable<T>> createAlgoController(elements:List<T>, target:T):AlgoStateController<T> {
        val iterator = LinearSearchIterator(elements, target)
       return AlgoControllerImpl(iterator = iterator, target = target)
    }
}