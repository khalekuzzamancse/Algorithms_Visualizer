package layers.ui.common_ui.decorators

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import layers.ui.common_ui.controll_section.TopBarControlSection
import layers.ui.common_ui.decorators.tab_layout.TabDecoratorController
import layers.ui.common_ui.decorators.tab_layout.TabDecoratorControllerImpl
import layers.ui.common_ui.decorators.tab_layout.TabLayoutDecorator
@Composable
fun SimulationSlot(
    modifier: Modifier = Modifier,
    state: SimulationScreenState,
    onEvent: (SimulationScreenEvent) -> Unit,
    resultSummary: @Composable ColumnScope.() -> Unit,
    pseudoCode: @Composable ColumnScope.() -> Unit,
    visualization: @Composable ColumnScope.() -> Unit,
) {
    TopBarControlSection(
        modifier = modifier,
        onNavIconClick = { onEvent(SimulationScreenEvent.NavigationRequest) },
        onResetRequest = { onEvent(SimulationScreenEvent.ResetRequest) },
        onAutoPlayRequest = { onEvent(SimulationScreenEvent.AutoPlayRequest) },
        onNext = { onEvent(SimulationScreenEvent.NextRequest) },
        showPseudocode = state.showPseudocode,
        showNavigationSection = state.showNavTabs,
        onCodeVisibilityToggleRequest = { onEvent(SimulationScreenEvent.CodeVisibilityToggleRequest) },
        onToggleNavigationSection = { onEvent(SimulationScreenEvent.ToggleNavigationSection) }
    ) {
        TabLayoutDecorator(
            modifier = Modifier,
            controller = state.tabController,
            showTabs = state.showNavTabs,
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedVisibility(state.showVisualization) {
                        visualization()
                        Spacer(Modifier.height(16.dp))
                    }

                    AnimatedVisibility(state.showResultSummary) {
                        resultSummary()
                        Spacer(Modifier.height(16.dp))
                    }
                    AnimatedVisibility(state.showPseudocode) {
                        pseudoCode()
                    }
                }
            },
        )

    }


}
data class SimulationScreenState(
    val navigationIcon: ImageVector? = Icons.AutoMirrored.Filled.ArrowBack,
    val showPseudocode: Boolean = true,
    val showResultSummary: Boolean = true,
    val showNavTabs: Boolean = false,
    val showVisualization: Boolean = true,
    val tabController: TabDecoratorController = TabDecoratorControllerImpl(),
)

/**
 * Using sealed so that by mistake outer file or module can not create new event
 * so to make read only the event to the client
 */
sealed interface SimulationScreenEvent {
    data object NavigationRequest : SimulationScreenEvent
    data object ToggleNavigationSection : SimulationScreenEvent
    data object NextRequest : SimulationScreenEvent
    data object ResetRequest : SimulationScreenEvent
    data object CodeVisibilityToggleRequest : SimulationScreenEvent
    data object AutoPlayRequest : SimulationScreenEvent
}

