package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.search_bar.decorator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.list.AdaptiveList
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.search_bar.MySearchBar

@Composable
fun <T> SearchSection(
    items: List<T>,
    filterPredicate: (T, String) -> Boolean,
    onSearchExitRequest: () -> Unit,
    searchedItemDecorator: @Composable (T, highLightedText: String) -> Unit,
) {
    val uiState = remember {
        SearchDecoratorState(
            items,
            filterPredicate
        )
    }
    val active = uiState.active.collectAsState().value
    AnimatedVisibility(
        visible = active
    ) {
        SearchSection(
            query = uiState.query.collectAsState().value,
            onQueryChanged = uiState::onQueryChanged,
            active = active,
            onActiveChanged = uiState::onActiveChanged,
            onSearchExitRequest = {
                uiState.onSearchChanged(false)
                onSearchExitRequest()
            },
            result = uiState.results.collectAsState().value,
            searchedItemDecorator = searchedItemDecorator,
        )

    }


}

@Composable
private fun <T> SearchSection(
    query: String,
    onQueryChanged: (String) -> Unit,
    active: Boolean,
    onSearchExitRequest: () -> Unit = {},
    onActiveChanged: (Boolean) -> Unit,
    result: List<T>,
    searchedItemDecorator: @Composable (T, String) -> Unit,
) {
    MySearchBar(
        query = query,
        onQueryChange = onQueryChanged,
        active = active,
        onActiveChanged = onActiveChanged,
        modifier = Modifier,
        onGoBack = onSearchExitRequest
    ) {
        AdaptiveList(
            modifier = Modifier,
            items = result
        ) { item ->
            searchedItemDecorator(item, query)
        }


    }


}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
// fun <T> SearchSection(
//    showSearch: Boolean,
//    onShowSearchChanged: (Boolean) -> Unit,
//    query: String,
//    onQueryChanged: (String) -> Unit,
//    active: Boolean,
//    onActiveChanged: (Boolean) -> Unit,
//    result: List<T>,
//    navigationIcon: ImageVector? = null,
//    onNavigationClick: (() -> Unit)? = null,
//    itemDecorator: @Composable (T, String) -> Unit,
//    bottomNavbar: @Composable () -> Unit = {},
//    contentOnNoSearch: @Composable (Modifier) -> Unit,
//) {
//
//    Box {
//        if (!showSearch) {
//            Scaffold(
//                topBar = {
//                    TopAppBar(
//                        title = {},
//                        navigationIcon = {
//                            if (navigationIcon != null) {
//                                IconButton(
//                                    onClick = {
//                                        onNavigationClick?.invoke()
//                                    }) {
//                                    Icon(
//                                        imageVector = navigationIcon,
//                                        contentDescription = null
//                                    )
//                                }
//                            }
//
//                        },
//                        actions = {
//                            IconButton(
//                                onClick = {
//                                    onShowSearchChanged(true)
//                                    onActiveChanged(true)
//                                }
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Default.Search,
//                                    contentDescription = null
//                                )
//                            }
//                        },
//                        colors = TopAppBarDefaults.topAppBarColors(
//                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp)
//                        )
//                    )
//
//                },
//                bottomBar = bottomNavbar
//            ) {
//                contentOnNoSearch(Modifier.padding(it))
//            }
//        } else {
//            MySearchBar(
//                query = query,
//                onQueryChange = onQueryChanged,
//                active = active,
//                onActiveChanged = onActiveChanged,
//                modifier = Modifier,
//                onGoBack = {
//                    onShowSearchChanged(false)
//                },
//
//                ) {
//                AdaptiveList(
//                    modifier = Modifier,
//                    items = result
//                ) { item ->
//                    itemDecorator(item, query)
//                }
//            }
//        }
//
//    }
//
//
//}
//
