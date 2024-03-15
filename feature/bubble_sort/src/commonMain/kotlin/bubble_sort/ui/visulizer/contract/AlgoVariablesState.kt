package bubble_sort.ui.visulizer.contract

import layers.ui.common_ui.Variable

/**
 * Used as separate from other module  so that
 * that module can easily detach from other module
 * it hold the variable information of that current state
 */

data class AlgoVariablesState(
    val name: String,
    val value: String? = null
)