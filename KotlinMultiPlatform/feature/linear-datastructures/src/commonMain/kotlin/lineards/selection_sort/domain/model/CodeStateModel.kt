package lineards.selection_sort.domain.model

data class CodeStateModel(
    val len: String? = null,
    val list:String?=null,
    val minIndex: String? = null,
    val isMinFound: String? = null,
    val i: String? = null,
    val swap: String? = null
) {
    fun allOutOfScope(): CodeStateModel {
        return this.copy(
            len = null,
            minIndex = null,
            isMinFound = null,
            i = null,
            swap = null
        )
    }

    fun lenDead(): CodeStateModel = this.copy(len = null)
    fun minIndexDead(): CodeStateModel = this.copy(minIndex = null)
    fun isMinFoundDead(): CodeStateModel = this.copy(isMinFound = null)
    fun iDead(): CodeStateModel = this.copy(i = null)
    fun swapDead(): CodeStateModel = this.copy(swap = null)
}
