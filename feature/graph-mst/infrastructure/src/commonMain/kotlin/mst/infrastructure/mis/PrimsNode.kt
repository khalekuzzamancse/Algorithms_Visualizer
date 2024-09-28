package mst.infrastructure.mis

import mst.domain.model.NodeModel

data class PrimsNode(
    val id: String,
    val label: String,
    var distance: Int= INFINITY,
    val addedToMST:Boolean,
    var parent: NodeModel?,
){
    companion object {
        const val  INFINITY =10000
    }
    /**
     * - Important to override the equal otherwise can cause unexpected result
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NodeModel) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}