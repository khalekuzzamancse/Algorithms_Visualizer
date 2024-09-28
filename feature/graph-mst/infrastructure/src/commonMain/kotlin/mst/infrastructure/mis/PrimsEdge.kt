package mst.infrastructure.mis

/**
 * - in case of dijkstra the Edge cost is present
 */
data class PrimsEdge(
    val id:String="",
    val u: PrimsNode,
    val v: PrimsNode,
    val cost:Int,
){
    override fun toString(): String {

        return "(${u.label}->${v.label}=$cost)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PrimsEdge) return false
        return u.id == other.u.id && v.id == other.v.id
    }

    // Override hashCode to include only 'from.id' and 'to.id'
    override fun hashCode(): Int {
        var result = u.id.hashCode()
        result = 31 * result + v.id.hashCode()
        return result
    }
}