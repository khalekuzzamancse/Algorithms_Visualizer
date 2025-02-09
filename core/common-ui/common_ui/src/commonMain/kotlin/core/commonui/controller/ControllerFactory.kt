package core.commonui.controller

object ControllerFactory {
    fun createAutoPlayer( onNextCallback: () -> Unit): AutoPlayer = AutoPlayerImpl(onNextCallback)
}