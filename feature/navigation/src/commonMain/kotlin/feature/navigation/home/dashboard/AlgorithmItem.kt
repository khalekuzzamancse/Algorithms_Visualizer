package feature.navigation.home.dashboard

import androidx.compose.ui.graphics.vector.ImageVector

internal data class AlgorithmItem(
    val name: String,
    val icon: ImageVector? = null,
    val destination: Destination = Destination.None
)

internal data class Algorithms(
    val subjectName: String,
    val logo: ImageVector? = null,
    val item: List<AlgorithmItem>
)
