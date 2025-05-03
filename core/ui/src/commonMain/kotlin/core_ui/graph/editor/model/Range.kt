package core_ui.graph.editor.model

internal class Range(private val start: Float, private val end: Float) {
    fun contains(value: Float): Boolean {
        return value in start..end
    }
}