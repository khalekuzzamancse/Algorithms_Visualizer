package binary_search.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import binary_search.PackageLevelAccess

@PackageLevelAccess //avoid to use other layer such domain or data/infrastructure
@Composable
internal fun _ResultSummary(
    low: Int?,
    high: Int?,
    mid: Int?,
) {
    val isSuccess = true

    Column {
        Text(if (isSuccess) "Successfully Search" else "UnSuccessfully Search")
        if (isSuccess) {
            Text("Founded at index : $low")
        }
        Text("Low :$low")
        Text("High :$high")
        Text("Mid :$mid")

    }

}
