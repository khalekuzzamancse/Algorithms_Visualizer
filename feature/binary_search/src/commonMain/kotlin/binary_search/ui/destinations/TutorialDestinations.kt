package binary_search.ui.destinations

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import layers.ui.common_ui.decorators.tab_layout.TabDestination
import binary_search.PackageLevelAccess
import platform_contracts.WebPageLoader

@PackageLevelAccess //avoid to use other layer such domain or data/infrastructure
@Composable
internal fun TutorialDestinations(destination: TabDestination) {
    when (destination) {
        TabDestination.Theory -> {
            WebPageLoader(url = "https://khalekuzzamancse.github.io/documentations/docs/quick_sort/theory.html")

        }

        TabDestination.ComplexityAnalysis -> {
            WebPageLoader(url = "https://khalekuzzamancse.github.io/documentations/docs/quick_sort/complexity_analysis.html")

        }

        TabDestination.Pseudocode -> {
            WebPageLoader(url = "https://khalekuzzamancse.github.io/documentations/docs/quick_sort/steps_n_pseucode.html")

        }

        TabDestination.Implementation -> {
            WebPageLoader(url = "https://khalekuzzamancse.github.io/documentations/docs/quick_sort/implementaion.html")
        }
        else-> {
            Text("Invalid Destination")
        }

    }

}