package linearsearch.ui.presentationlogic

import linearsearch.ui.presentationlogic.AutoPlayerImpl
import linearsearch.ui.presentationlogic.Controller

object PresentationFactory {
    fun createAutoPlayer(): Controller.AutoPlayer = AutoPlayerImpl()
}