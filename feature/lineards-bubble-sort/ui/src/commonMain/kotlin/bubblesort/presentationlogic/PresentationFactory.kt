package bubblesort.presentationlogic

object PresentationFactory {
    fun createAutoPlayer():Controller.AutoPlayer=AutoPlayerImpl()
}