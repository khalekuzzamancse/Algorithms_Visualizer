package linearsearch.infrastructure

import linearsearch.domain.model.DataModel

object InfrastructureFactory {

    fun <T:Comparable<T>>createSimulator(model: DataModel<T>): SimulatorImpl<T> =
    SimulatorImpl(model)
}