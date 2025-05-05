package graph.djkstra.domain.model


data class NodeModel(
    val id: String,
    val label: String,
    var distance: Int=10000
) {
    companion object {
        const val  INFINITY =10000
    }
    override fun toString():String{
       // val distance=if(distance == INFINITY)"inf" else distance.toString()// this "∞" may not be visible in console or terminal but visible in ui
        return "( $label, $distance )"

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


