package layers.ui.common_ui.decorators.tab_layout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LineAxis
import androidx.compose.material.icons.twotone.Code
import androidx.compose.material.icons.twotone.Language
import androidx.compose.material.icons.twotone.LineAxis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TabDecoratorControllerImpl() :TabDecoratorController{
    override val tabs: List<TabItem> =listOf(
        TabItem(
            label = "Visualization",
            unFocusedIcon = Icons.Outlined.AutoGraph,
            focusedIcon = Icons.Filled.AutoGraph
        ),
//        TabItem(
//            label = "Theory",
//            unFocusedIcon = Icons.Outlined.Description,
//            focusedIcon = Icons.Filled.Description
//        ),
//        TabItem(
//            label = "Complexity Analysis",
//            unFocusedIcon = Icons.Outlined.LineAxis,
//            focusedIcon = Icons.TwoTone.LineAxis
//        ),
//        TabItem(
//            label = "Pseudocode",
//            unFocusedIcon = Icons.Outlined.Language,
//            focusedIcon = Icons.TwoTone.Language
//        ),
//        TabItem(
//            label = "Implementation",
//            unFocusedIcon = Icons.Outlined.Code,
//            focusedIcon = Icons.TwoTone.Code
//        )
    )
    private val _selected=MutableStateFlow(TabDestination.Visualization)
    override val selected: Flow<TabDestination> =_selected.asStateFlow()
    override fun onDestinationSelected(destination: TabDestination) {
        _selected.update { destination }
    }

}