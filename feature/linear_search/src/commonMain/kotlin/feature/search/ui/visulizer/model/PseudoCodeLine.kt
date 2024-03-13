package feature.search.ui.visulizer.model

internal data class PseudoCodeLine(
    val line: String,
    val lineNumber:Int,
    val highLighting:Boolean=false,
)