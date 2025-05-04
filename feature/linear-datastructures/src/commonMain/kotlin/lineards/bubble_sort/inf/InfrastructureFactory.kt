package lineards.bubble_sort.inf

import lineards.bubble_sort.domain.model.DataModel

object InfrastructureFactory {

    fun <T:Comparable<T>>createSimulator(model: DataModel<T>): SimulatorImpl<T> =
    SimulatorImpl(model)
}