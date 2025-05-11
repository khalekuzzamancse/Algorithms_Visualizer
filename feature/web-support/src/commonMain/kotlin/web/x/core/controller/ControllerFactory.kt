package web.x.core.controller

object ControllerFactory {
    fun createAutoPlayer( onNextCallback: () -> Unit): AutoPlayer = AutoPlayerImpl(onNextCallback)
}