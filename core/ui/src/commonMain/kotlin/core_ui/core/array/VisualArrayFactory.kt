package core_ui.core.array

import core_ui.core.array.controller.VisualArrayController
import core_ui.core.array.controller.VisualArrayControllerImpl


object VisualArrayFactory {

    fun createController(
        itemLabels: List<String>,
        pointersLabel: List<String> = emptyList()
    ): VisualArrayController =
        VisualArrayControllerImpl(itemLabels = itemLabels, pointersLabel = pointersLabel)
}