package feature.search.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import feature.search.PackageLevelAccess

@PackageLevelAccess //avoid to use other layer such domain or data/infrastructure
@Composable
 internal fun _ResultSummary(
    foundedIndex: Int,
    comparisons: Int
) {
    val isSuccess = foundedIndex!= -1

        Column {
            Text(if (isSuccess)"Successfully Search" else "UnSuccessfully Search")
            if (isSuccess){
                Text("Founded at index : $foundedIndex")
            }
            Text("Total comparisons:$comparisons")

        }

}
