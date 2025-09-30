package lineards.insertion_sort.domain.model

data class CodeStateModel(
    val len: String? = null,
    val list: String? = null,
    val i: String? = null,
    val j: String? = null,
    val shouldSwap: String? = null,
    /**list[j]*/
    val valueAtJ:String? = null,
    /**list[j+1]*/
    val valueAtJmiuns1:String? = null,
    val swap: String? = null
) {
    fun allOutOfScope(): CodeStateModel {
        return this.copy(
            len = null,
            list = null,
            i = null,
            j = null,
            shouldSwap = null,
            swap = null
        )
    }

    fun lenDead(): CodeStateModel = this.copy(len = null)
    fun sortedListDead(): CodeStateModel = this.copy(list = null)
    fun iDead(): CodeStateModel = this.copy(i = null)
    fun jDead(): CodeStateModel = this.copy(j = null, valueAtJ = null, valueAtJmiuns1 = null)
    fun shouldSwapDead(): CodeStateModel = this.copy(shouldSwap = null)
    fun swapDead(): CodeStateModel = this.copy(swap = null)
}
