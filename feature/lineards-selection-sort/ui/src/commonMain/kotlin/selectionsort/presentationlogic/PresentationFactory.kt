package selectionsort.presentationlogic

object PresentationFactory {
    fun createAutoPlayer(): Controller.AutoPlayer = AutoPlayerImpl()
}