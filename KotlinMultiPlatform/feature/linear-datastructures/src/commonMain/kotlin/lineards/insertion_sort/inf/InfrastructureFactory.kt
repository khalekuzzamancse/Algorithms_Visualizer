package lineards.insertion_sort.inf

import lineards.insertion_sort.domain.model.DataModel

object InfrastructureFactory {

    fun <T:Comparable<T>>createSimulator(model: DataModel<T>): SimulatorImpl<T> =
    SimulatorImpl(model)
}