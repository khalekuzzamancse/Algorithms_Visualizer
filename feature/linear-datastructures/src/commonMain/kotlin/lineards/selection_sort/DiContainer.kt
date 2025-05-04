package lineards.selection_sort
import lineards.selection_sort.domain.model.DataModel
import lineards.selection_sort.inf.InfrastructureFactory
object DiContainer {
    fun <T : Comparable<T>> createSimulator(model: DataModel<T>) =
        InfrastructureFactory.createSimulator(model)
}