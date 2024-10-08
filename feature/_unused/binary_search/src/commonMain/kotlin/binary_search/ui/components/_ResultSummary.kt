package binary_search.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import binary_search.PackageLevelAccess
import binary_search.domain.VisualizationState

@PackageLevelAccess //avoid to use other layer such domain or data/infrastructure
@Composable
internal fun _ResultSummary(
    endState: VisualizationState.Finished
) {
    val isSuccess = endState.foundedAt != null
    Column {
        Text(if (isSuccess) "Successfully Search" else "UnSuccessfully Search")
        if (isSuccess) {
            Text("Founded at index : ${endState.foundedAt}")
        }
        Text("Comparisons :${endState.comparisons}")
    }

}
