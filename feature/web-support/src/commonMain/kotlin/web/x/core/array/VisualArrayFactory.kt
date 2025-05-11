package web.x.core.array

import web.x.core.array.controller.VisualArrayController
import web.x.core.array.controller.VisualArrayControllerImpl

object VisualArrayFactory {

    fun createController(
        itemLabels: List<String>,
        pointersLabel: List<String> = emptyList()
    ): VisualArrayController =
        VisualArrayControllerImpl(itemLabels = itemLabels, pointersLabel = pointersLabel)
}