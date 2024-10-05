package mst.presentationlogic.controller

/**
 * This layer should not depend on on any libraries or UI implementations such as GraphViewController
 * that is why exposing some event from here
 */
interface Controller {


    interface AutoPlayer {
        fun isAutoPlayMode(): Boolean
        fun autoPlayRequest(delay: Int)

        /**Must dismiss it if no longer required because it has coroutines run,we have to stop it*/
        fun dismiss()
        var onNextCallback: () -> Unit
    }



}