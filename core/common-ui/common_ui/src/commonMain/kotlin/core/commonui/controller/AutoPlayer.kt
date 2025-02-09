package core.commonui.controller

interface AutoPlayer {
    fun isAutoPlayMode(): Boolean
    fun autoPlayRequest(delay: Int)

    /**Must dismiss it if no longer required because it has coroutines run,we have to stop it*/
    fun dismiss()
    var onNextCallback: () -> Unit
}