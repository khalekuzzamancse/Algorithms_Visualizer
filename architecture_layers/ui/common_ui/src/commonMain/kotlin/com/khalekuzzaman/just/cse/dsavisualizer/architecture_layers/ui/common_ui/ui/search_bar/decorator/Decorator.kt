package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.search_bar.decorator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.list.AdaptiveList
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.search_bar.MySearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> SearchDecoratorBottomSheet(
    showSearch: Boolean,
    onShowSearchChanged: (Boolean) -> Unit,
    query: String,
    onQueryChanged: (String) -> Unit,
    active: Boolean,
    onActiveChanged: (Boolean) -> Unit,
    result: List<T>,
    navigationIcon: ImageVector? = null,
    onNavigationClick: (() -> Unit)? = null,
    itemDecorator: @Composable (T, String) -> Unit,
    sheetContent: @Composable () -> Unit = {},
    contentOnNoSearch: @Composable (Modifier) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    Box {
        if (!showSearch) {
            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            if (navigationIcon != null) {
                                IconButton(
                                    onClick = {
                                        onNavigationClick?.invoke()
                                    }) {
                                    Icon(
                                        imageVector = navigationIcon,
                                        contentDescription = null
                                    )
                                }
                            }

                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    onShowSearchChanged(true)
                                    onActiveChanged(true)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp)
                        )
                    )

                },
                sheetContent = {
                    sheetContent()

                },
                sheetSwipeEnabled = true
            ) {
                contentOnNoSearch(Modifier.padding(it))
            }
        } else {
            MySearchBar(
                query = query,
                onQueryChange = onQueryChanged,
                active = active,
                onActiveChanged = onActiveChanged,
                modifier = Modifier,
                onGoBack = {
                    onShowSearchChanged(false)
                },

                ) {
                AdaptiveList(
                    modifier = Modifier,
                    items = result
                ) { item ->
                    itemDecorator(item, query)
                }
            }
        }

    }


}
