package mst.domain.model


data class NodeModel(
    val id: String,
    val label: String,
    var distanceFromParent: Int = 10000,
    var parent: NodeModel?=null
) {
    companion object {
        const val INFINITY = 10000
    }

    override fun toString(): String {
        // val distance=if(distance == INFINITY)"inf" else distance.toString()// this "∞" may not be visible in console or terminal but visible in ui
        return "( $label, $distanceFromParent )"

    }
    // Override equals to compare nodes based on their unique ID
    override fun equals(other: Any?): Boolean {
        if (this === other) return true  // Reference equality check
        if (other !is NodeModel) return false  // Type check
        return id == other.id  // Compare based on node ID
    }

    // Override hashCode to generate hash based on ID
    override fun hashCode(): Int {
        return id.hashCode()
    }


}


