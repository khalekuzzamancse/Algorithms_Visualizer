package selectionsort.infrastructure

import selectionsort.domain.model.DataModel

object InfrastructureFactory {

    fun <T:Comparable<T>>createSimulator(model: DataModel<T>): SimulatorImpl<T> =
    SimulatorImpl(model)
}