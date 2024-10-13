package bubblesort.domain.model;


data class CodeStateModel(
    val len: String?=null,
    val sortedList:String?=null,
    val j: String?=null,
    val i:String? = null,
    /**list[j]*/
    val valueAtJ:String? = null,
    /**list[j+1]*/
    val valueAtJPlus1:String? = null,
    val shouldSwap:String? = null,
    /**
     * - Pass the swap pair such as, `swap(4,5)
     */
    val swap:String? = null
){
    fun allOutOfScope(): CodeStateModel {
        return this.copy(
            len = null,
            sortedList = null,
            j = null,
            i = null,
            valueAtJ = null,
            valueAtJPlus1 = null,
            shouldSwap = null
        )
    }
    fun lenDead(): CodeStateModel = this.copy(len = null)
    fun sortedListDead(): CodeStateModel = this.copy(sortedList = null)
    fun jDead(): CodeStateModel = this.copy(j = null)
    fun iDead(): CodeStateModel = this.copy(i = null)
    fun valueAtJDead(): CodeStateModel = this.copy(valueAtJ = null)
    fun valueAtJPlus1Dead(): CodeStateModel = this.copy(valueAtJPlus1 = null)
    fun shouldSwapDead(): CodeStateModel = this.copy(shouldSwap = null)
    fun swapDead(): CodeStateModel = this.copy(swap = null)

}