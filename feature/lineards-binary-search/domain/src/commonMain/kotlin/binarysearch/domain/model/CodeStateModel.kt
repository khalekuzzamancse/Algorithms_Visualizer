package binarysearch.domain.model

/**
 * @param target can be T, that is why taking String
 * @param current can be T, that is why taking String
 */
data class CodeStateModel(
    val target: String?,
    val low: Int?=null,
    val high: Int?=null,
    val mid: Int? = null,
    val current: String? = null,
    val isFound: String? = null,
    val currentLessThanTarget: String? = null,
    val currentGreaterThanTarget: String? = null,
    val returnIndex: Int? = null,
)
