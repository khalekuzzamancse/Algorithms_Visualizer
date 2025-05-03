package core.ui.core.array

import core.commonui.array.controller.VisualArrayController
import core.commonui.array.controller.VisualArrayControllerImpl

object VisualArrayFactory {

    fun createController(
        itemLabels: List<String>,
        pointersLabel: List<String> = emptyList()
    ): VisualArrayController =
        VisualArrayControllerImpl(itemLabels = itemLabels, pointersLabel = pointersLabel)
}