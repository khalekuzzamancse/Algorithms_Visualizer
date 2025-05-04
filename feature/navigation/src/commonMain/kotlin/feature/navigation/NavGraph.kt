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
import dijkstra.ui.DijkstraSimulationScreen
import feature.navigation.drawer.NavDestination
import graphbfs.ui.ui.BFSSimulation
import graphtopologicalsort.ui.TopologicalSort
import graphtraversal.ui.DfsSimulation

import lineards.binary_search.presentation.BinarySearchRoute
import lineards.bubble_sort.presentation.BubbleSortRoute
import lineards.insertion_sort.presentation.InsertionSortRoute
import lineards.linear_search.presentation.LinearSearchRoute
import lineards.quick_sort.ui.QuickSortScreen
import lineards.selection_sort.presenation.SelectionSortRoute
import mst.ui.PrimsSimulationScreen

import tree.binary.BSTView
import tree.binary.expression_tree.ExpressionTree
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
            LinearSearchRoute (
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
            BSTView(
                navigationIcon = {
                    _DrawerIcon(
                        isNavRailMode = isNavRailMode,
                        onClick = openDrawerRequest
                    )
                }
            )
        }
        composable(NavDestination.ExpressionSearchTree.route) {
            ExpressionTree(
                onNavBack = {
                    popBackStack()
                }
            )
//            BSTView(
//                navigationIcon = {
//                    _DrawerIcon(
//                        isNavRailMode = isNavRailMode,
//                        onClick = openDrawerRequest
//                    )
//                }
//            )
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