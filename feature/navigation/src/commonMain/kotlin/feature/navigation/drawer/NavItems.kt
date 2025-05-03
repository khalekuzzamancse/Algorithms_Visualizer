package feature.navigation.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.automirrored.outlined.Sort
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Pageview
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.DeviceHub
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Pageview
import androidx.compose.material.icons.outlined.Share

//TODO

sealed interface NavDestination : Destination {
    data object Home : NavDestination {
        override val route = "Home"
    }

    data object LinearSearch : NavDestination {
        override val route = "LinearSearch"
    }

    data object BinarySearch : NavDestination {
        override val route = "BinarySearch"
    }

    data object BubbleSort : NavDestination {
        override val route = "BubbleSort"
    }

    data object SelectionSort : NavDestination {
        override val route = "SelectionSort"
    }

    data object InsertionSort : NavDestination {
        override val route = "InsertionSort"
    }

    data object QuickSort : NavDestination {
        override val route = "QuickSort"
    }

    data object BreadthFirstSearch : NavDestination {
        override val route = "BreadthFirstSearch"
    }

    data object DepthFirstSearch : NavDestination {
        override val route = "DepthFirstSearch"
    }

    data object TopologicalSort : NavDestination {
        override val route = "TopologicalSort"
    }

    data object DijkstraAlgorithm : NavDestination {
        override val route = "DijkstraAlgorithm"
    }

    data object PrimsAlgorithm : NavDestination {
        override val route = "PrimsAlgorithm"
    }

    data object TreeTraversals : NavDestination {
        override val route = "TreeTraversals"
    }
    data object BinarySearchTree : NavDestination {
        override val route = "BinarySearchTree"
    }
    data object ExpressionSearchTree : NavDestination {
        override val route = "ExpressionSearchTree"
    }

    data object AboutUs : NavDestination {
        override val route = "AboutUs"
    }
}

object NavDestinationBuilder {

    val navGroups = listOf(group1(), group2(), group3(), group4(), group5(), group6())

    val allDestinations: List<Destination> = navGroups.flatMap { group ->
        group.items.map { it.destination }
    }

    private fun group1() = NavGroup(
        items = listOf(
            NavigationItem(
                label = "Home",
                unFocusedIcon = Icons.Outlined.Home,
                focusedIcon = Icons.Filled.Home,
                destination = NavDestination.Home
            ),
        )
    )

    private fun group2() = NavGroup(
        items = listOf(
            NavigationItem(
                label = "Linear Search",
                unFocusedIcon = Icons.Outlined.Pageview,
                focusedIcon = Icons.Filled.Pageview,
                destination = NavDestination.LinearSearch
            ),
            NavigationItem(
                label = "Binary Search",
                unFocusedIcon = Icons.Outlined.Pageview,
                focusedIcon = Icons.Filled.Pageview,
                destination = NavDestination.BinarySearch
            ),
        )
    )

    private fun group3() = NavGroup(
        items = listOf(
            NavigationItem(
                label = "Bubble Sort",
                unFocusedIcon = Icons.AutoMirrored.Outlined.Sort,
                focusedIcon = Icons.AutoMirrored.Filled.Sort,
                destination = NavDestination.BubbleSort
            ),
            NavigationItem(
                label = "Selection Sort",
                unFocusedIcon = Icons.AutoMirrored.Outlined.Sort,
                focusedIcon = Icons.AutoMirrored.Filled.Sort,
                destination = NavDestination.SelectionSort
            ),
            NavigationItem(
                label = "Insertion Sort",
                unFocusedIcon = Icons.AutoMirrored.Outlined.Sort,
                focusedIcon = Icons.AutoMirrored.Filled.Sort,
                destination = NavDestination.InsertionSort
            ),
            NavigationItem(
                label = "Quick Sort",
                unFocusedIcon = Icons.AutoMirrored.Outlined.Sort,
                focusedIcon = Icons.AutoMirrored.Filled.Sort,
                destination = NavDestination.QuickSort
            )
        )
    )

    private fun group4() = NavGroup(
        items = listOf(
            NavigationItem(
                label = "Breadth First Search",
                unFocusedIcon = Icons.Outlined.Share,
                focusedIcon = Icons.Filled.Share,
                destination = NavDestination.BreadthFirstSearch
            ),
            NavigationItem(
                label = "Depth First Search",
                unFocusedIcon = Icons.Outlined.Share,
                focusedIcon = Icons.Filled.Share,
                destination = NavDestination.DepthFirstSearch
            ),
            NavigationItem(
                label = "Topological Sort",
                unFocusedIcon = Icons.Outlined.Share,
                focusedIcon = Icons.Filled.Share,
                destination = NavDestination.TopologicalSort
            ),
            NavigationItem(
                label = "Dijkstra Algorithm",
                unFocusedIcon = Icons.Outlined.Share,
                focusedIcon = Icons.Filled.Share,
                destination = NavDestination.DijkstraAlgorithm
            ),
            NavigationItem(
                label = "Prims Algorithm",
                unFocusedIcon = Icons.Outlined.Share,
                focusedIcon = Icons.Filled.Share,
                destination = NavDestination.PrimsAlgorithm
            ),
        )
    )

    private fun group5() = NavGroup(
        items = listOf(
            NavigationItem(
                label = "Tree Traversals",
                unFocusedIcon = Icons.Outlined.DeviceHub,
                focusedIcon = Icons.Filled.DeviceHub,
                destination = NavDestination.TreeTraversals
            ),
            NavigationItem(
                label = "Binary Search Tree",
                unFocusedIcon = Icons.Outlined.DeviceHub,
                focusedIcon = Icons.Filled.DeviceHub,
                destination = NavDestination.BinarySearchTree
            ),
            NavigationItem(
                label = "Expression Tree",
                unFocusedIcon = Icons.Outlined.DeviceHub,
                focusedIcon = Icons.Filled.DeviceHub,
                destination = NavDestination.ExpressionSearchTree
            )
        )
    )

    private fun group6() = NavGroup(
        items = listOf(
            NavigationItem(
                label = "About Us",
                unFocusedIcon = Icons.Outlined.Info,
                focusedIcon = Icons.Filled.Info,
                destination = NavDestination.AboutUs
            ),
        )
    )
}
