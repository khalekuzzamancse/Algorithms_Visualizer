package layers.ui.common_ui.decorators.tab_layout

import kotlinx.coroutines.flow.Flow

sealed interface TabDecoratorController{
    val tabs:List<TabItem>
    val selected: Flow<TabDestination>
    fun onDestinationSelected(destination: TabDestination)
}