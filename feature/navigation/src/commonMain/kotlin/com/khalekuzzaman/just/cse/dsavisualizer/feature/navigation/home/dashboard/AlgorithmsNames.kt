package com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation.home.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.ManageHistory
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Train

internal object AlgorithmsNames{
     val dsaAlgorithms = Algorithms(
        subjectName = "Data Structure and Algorithms",
        item = listOf(
            AlgorithmItem(name = "Linear Search", icon = Icons.Filled.Search, destination = Destination.LinearSearch),
            AlgorithmItem(name = "Binary Search", icon = Icons.Filled.Search,destination = Destination.BinarySearch),
            AlgorithmItem(name = "Bubble Sort", icon = Icons.AutoMirrored.Filled.Sort,destination = Destination.BubbleSort),
            AlgorithmItem(name = "Selection Sort", icon = Icons.AutoMirrored.Filled.Sort),
            AlgorithmItem(name = "Insertion Sort", icon = Icons.AutoMirrored.Filled.Sort),
            AlgorithmItem(name = "Counting Sort", icon = Icons.AutoMirrored.Filled.Sort),
            AlgorithmItem(name = "Heap Sort", icon = Icons.AutoMirrored.Filled.Sort),
            AlgorithmItem(name = "Inorder Traversal", icon = Icons.Default.Train),
            AlgorithmItem(name = "Postorder Traversal", icon = Icons.Default.Train),
            AlgorithmItem(name = "Preorder Traversal", icon = Icons.Default.Train),
            AlgorithmItem(name = "Bread First Search", icon = Icons.Filled.Search),
            AlgorithmItem(name = "Depth First Search", icon = Icons.Filled.Search),
            AlgorithmItem(name = "Dijkstra Shortest Path", icon = Icons.Default.Timeline),
            AlgorithmItem(name = "Bellman Form", icon = Icons.Default.Timeline),

            )
    )

    // Algorithms for OS (Operating Systems) course
     val osAlgorithms = Algorithms(
        subjectName = "Operating System",
        item = listOf(
            AlgorithmItem("Process Scheduling", Icons.Default.Schedule),
            AlgorithmItem("Memory Management", Icons.Default.Memory),
            AlgorithmItem("File System", Icons.Default.Memory),
            AlgorithmItem("Concurrency Control", Icons.Default.ManageHistory),
            AlgorithmItem("Deadlock Handling", Icons.Default.ManageHistory),
            AlgorithmItem("Disk Scheduling", Icons.Default.Schedule),
            AlgorithmItem("CPU Scheduling", Icons.Default.Schedule)
        )
    )
}
