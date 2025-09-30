package core.ui

interface CoreSimulator {
    fun isFinished(): Boolean
    fun getRawPseudocode(): String
}