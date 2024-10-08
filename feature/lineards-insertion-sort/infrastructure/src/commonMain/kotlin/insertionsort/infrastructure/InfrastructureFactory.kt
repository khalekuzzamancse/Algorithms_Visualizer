package insertionsort.infrastructure

import insertionsort.domain.model.DataModel

object InfrastructureFactory {

    fun <T:Comparable<T>>createSimulator(model: DataModel<T>): SimulatorImpl<T> =
    SimulatorImpl(model)
}