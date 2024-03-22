package feature.search.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import feature.search.PackageLevelAccess
import feature.search.domain.VisualizationState

@PackageLevelAccess //avoid to use other layer such domain or data/infrastructure
@Composable
internal fun _ResultSummary(endState: VisualizationState.Finished) {
    val foundedIndex = endState.foundedIndex
    val comparisons = endState.comparisons
    val isSuccess = foundedIndex != -1

    Column {
        Text(if (isSuccess) "Successfully Search" else "UnSuccessfully Search")
        if (isSuccess) {
            Text("Founded at index : $foundedIndex")
        }
        Text("Total comparisons:$comparisons")

    }

}
