package domain.model

import domain.model.NodeModel.Companion.INFINITY

/**
 * - in case of dijkstra the Edge cost is present
 */
data class EdgeModel(
    val id:String="",
    val u: NodeModel,
    val v: NodeModel,
    val cost:Int,
){
    override fun toString(): String {
        val cost=if(cost == INFINITY)"∞" else cost.toString()
        return "(${u.label}->${v.label}=$cost)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EdgeModel) return false
        return u.id == other.u.id && v.id == other.v.id
    }

    // Override hashCode to include only 'from.id' and 'to.id'
    override fun hashCode(): Int {
        var result = u.id.hashCode()
        result = 31 * result + v.id.hashCode()
        return result
    }
}