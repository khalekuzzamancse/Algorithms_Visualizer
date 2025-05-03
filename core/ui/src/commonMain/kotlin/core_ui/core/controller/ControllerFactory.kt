package core_ui.core.controller

object ControllerFactory {
    fun createAutoPlayer( onNextCallback: () -> Unit): AutoPlayer = AutoPlayerImpl(onNextCallback)
}