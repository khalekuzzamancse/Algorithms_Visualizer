package binarysearch.presentationlogic

object PresentationFactory {
    fun createAutoPlayer(): Controller.AutoPlayer = AutoPlayerImpl()
}