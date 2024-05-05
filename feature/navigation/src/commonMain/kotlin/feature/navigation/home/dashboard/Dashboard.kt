package feature.navigation.home.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import feature.navigation.home.dashboard.AlgorithmsNames.dsaAlgorithms
import feature.navigation.home.dashboard.AlgorithmsNames.osAlgorithms



@Composable
internal fun DashBoard(
    onNavigationRequest: (Destination) -> Unit,
) {
    val course = listOf(dsaAlgorithms, osAlgorithms)
    val state = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        modifier = Modifier,
        columns = StaggeredGridCells.Adaptive(minSize = 200.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 24.dp,
        state = state,
    ) {
        course.forEach { algorithms ->
            item(
                span = StaggeredGridItemSpan.FullLine
            ) {
                _SubjectName(name = algorithms.subjectName)
            }
            items(algorithms.item) { item ->
                _AlgorithmName(
                    name = item.name, icon = item.icon, onClick = {
                        onNavigationRequest(item.destination)
                    })
            }
        }

    }


}


@Composable
private fun _AlgorithmName(
    modifier: Modifier = Modifier,
    icon: ImageVector?,
    name: String,
    onClick: () -> Unit,
) {
    Surface(
        shadowElevation = 6.dp,
        modifier = modifier
            .selectable(
                selected = true,
                onClick = onClick,
                enabled = true,
                role = Role.Button,
            ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                modifier = Modifier, text = name,
                style = MaterialTheme.typography.titleMedium.copy(
                    //  fontWeight = FontWeight.SemiBold,
                    // color = MaterialTheme.colorScheme.primary
                ),
            )
        }

    }

}

@Composable
private fun _SubjectName(
    modifier: Modifier = Modifier,
    name: String
) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 4.dp
        ) {
            Text(
                modifier = Modifier
                    //  .background(MaterialTheme.colorScheme.tertiary)
                    .padding(16.dp), text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }


}
