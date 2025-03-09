package feature.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import binarysearch.ui.BinarySearchRoute
import bubblesort.ui.BubbleSortRoute
import dijkstra.ui.DijkstraSimulationScreen
import feature.navigation.drawer.NavDestination
import graphbfs.ui.ui.BFSSimulation
import graphtopologicalsort.ui.TopologicalSort
import graphtraversal.ui.DfsSimulation
import insertionsort.ui.InsertionSortRoute
import linearsearch.ui.LinearSearchRoute
import mst.ui.PrimsSimulationScreen
import quick_sort.ui.QuickSortScreen
import selectionsort.ui.SelectionSortRoute
import tree.binary.BinarySearchTree
import tree.ui.TreeSimulationScreen


fun NavController.createNavGraph(
    isNavRailMode: Boolean,
    openDrawerRequest: () -> Unit,
    onAboutUsRequest:()->Unit,
): NavGraph {
    return createGraph(startDestination = NavDestination.Home.route) {
        composable(NavDestination.Home.route) {
            HomeRoute(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                },
                onAboutUsRequest = onAboutUsRequest
            )
        }
        composable(NavDestination.LinearSearch.route) {
            LinearSearchRoute(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                }
            )

        }
        composable(NavDestination.BinarySearch.route) {
            BinarySearchRoute(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                }
            )
        }
        composable(NavDestination.BubbleSort.route) {
            BubbleSortRoute(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                }
            )

        }
        composable(NavDestination.SelectionSort.route) {
            SelectionSortRoute(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                }
            )

        }
        composable(NavDestination.InsertionSort.route) {
            InsertionSortRoute(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                }
            )
        }
        composable(NavDestination.QuickSort.route) {
            QuickSortScreen(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                }
            )
        }
        composable(NavDestination.BreadthFirstSearch.route) {
            BFSSimulation(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                }
            )
        }
        composable(NavDestination.DepthFirstSearch.route) {
            DfsSimulation(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                }
            )
        }
        composable(NavDestination.TopologicalSort.route) {
            TopologicalSort(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                }
            )
        }
        composable(NavDestination.DijkstraAlgorithm.route) {
            DijkstraSimulationScreen(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                }
            )
        }

        composable(NavDestination.PrimsAlgorithm.route) {
            PrimsSimulationScreen(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                }
            )
        }
        composable(NavDestination.TreeTraversals.route) {
            TreeSimulationScreen(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                }
            )
        }
        composable(NavDestination.BinarySearchTree.route) {
            BinarySearchTree(
//                navigationIcon = {
//                    _DrawerIcon(
//                        isNavRailMode = isNavRailMode,
//                        onClick = openDrawerRequest
//                    )
//                }
            )
        }
        composable(NavDestination.AboutUs.route) {
            AboutUsPage(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                }
            )
        }
    }

}

@Composable
private fun _DrawerIcon(
    modifier: Modifier = Modifier,
    isNavRailMode: Boolean,
    onClick: () -> Unit,
) {
    if (!isNavRailMode) {
        IconButton(
            modifier = modifier,
            onClick = onClick,
        ) {
            Icon(Icons.Default.Menu, contentDescription = "navigation")
        }
    }
}