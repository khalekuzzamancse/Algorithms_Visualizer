package lineards.linear_search.infrastructure

import lineards.linear_search.domain.model.DataModel

object InfrastructureFactory {

    fun <T:Comparable<T>>createSimulator(model: DataModel<T>, enablePseudocode:Boolean=true): SimulatorImpl<T> =
    SimulatorImpl(model,enablePseudocode)
}