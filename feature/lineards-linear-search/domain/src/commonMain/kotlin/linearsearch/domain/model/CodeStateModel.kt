package linearsearch.domain.model;

/**
 * @param target can be T, that is why taking String
 *   @param current can be T, that is why taking String
 */
data class CodeStateModel(
    val len: Int,
    val target: String,
    val i:Int? = null,
    val current:String? = null,
    val isFound:Boolean?=null,
    val returnIndex:Int? = null,
)