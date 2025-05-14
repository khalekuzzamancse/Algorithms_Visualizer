package lineards

import lineards.linear_search.domain.model.DataModel
import lineards.linear_search.infrastructure.InfrastructureFactory
import lineards.linear_search.presentation.LSSearchRouteController

internal object DiContainer {
    fun  lsSearchController(): LSSearchRouteController{
        return  LSSearchRouteController()
    }
    fun <T : Comparable<T>> createLinearSearchSimulator(model: DataModel<T>) =
        InfrastructureFactory.createSimulator(model)
    fun <T : Comparable<T>> createInsertionSortSimulator(model: lineards.insertion_sort.domain.model.DataModel<T>) =
        lineards.insertion_sort.inf.InfrastructureFactory.createSimulator(model)
    fun <T : Comparable<T>> createBubbleSortSimulator(model: lineards.bubble_sort.domain.model.DataModel<T>) =
        lineards.bubble_sort.inf.InfrastructureFactory.createSimulator(model)
    fun <T : Comparable<T>> createBinarySearchSimulator(model: lineards.binary_search.domain.model.DataModel<T>) =
        lineards.binary_search.infrastructure.InfrastructureFactory.createSimulator(model)
    fun <T : Comparable<T>> createSelectionSortSimulator(model: lineards.selection_sort.domain.model.DataModel<T>) =
        lineards.selection_sort.inf.InfrastructureFactory.createSimulator(model)
}