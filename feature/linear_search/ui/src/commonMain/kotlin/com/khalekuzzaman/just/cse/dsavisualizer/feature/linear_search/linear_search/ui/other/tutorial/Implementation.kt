package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.other.tutorial

import androidx.compose.runtime.Composable
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.section.tutorial.TutorialContent
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.section.implementation.TutorialImplementationSection

@Composable
fun ImplementationPreview() {
    TutorialImplementationSection(linearSearchImplementations)
}
private val linearSearchImplementations = listOf(
    TutorialContent.Implementation(
        languageName = "C",
        code = """
            #include <stdio.h>
            
            int linearSearch(int arr[], int n, int key) {
                for (int i = 0; i < n; i++) {
                    if (arr[i] == key) {
                        return i; // Return the index if key is found
                    }
                }
                return -1; // Return -1 if key is not found
            }
            
            int main() {
                int arr[] = {1, 2, 3, 4, 5};
                int n = sizeof(arr) / sizeof(arr[0]);
                int key = 3;
                int result = linearSearch(arr, n, key);
                if (result != -1) {
                    printf("Element %d found at index %d", key, result);
                } else {
                    printf("Element %d not found", key);
                }
                return 0;
            }
        """.trimIndent()
    ),
    TutorialContent.Implementation(
        languageName = "C++",
        code = """
            #include <iostream>
            
            int linearSearch(int arr[], int n, int key) {
                for (int i = 0; i < n; i++) {
                    if (arr[i] == key) {
                        return i; // Return the index if key is found
                    }
                }
                return -1; // Return -1 if key is not found
            }
            
            int main() {
                int arr[] = {1, 2, 3, 4, 5};
                int n = sizeof(arr) / sizeof(arr[0]);
                int key = 3;
                int result = linearSearch(arr, n, key);
                if (result != -1) {
                    std::cout << "Element " << key << " found at index " << result << std::endl;
                } else {
                    std::cout << "Element " << key << " not found" << std::endl;
                }
                return 0;
            }
        """.trimIndent()
    ),
    TutorialContent.Implementation(
        languageName = "Java",
        code = """
            public class LinearSearch {
                static int linearSearch(int[] arr, int key) {
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i] == key) {
                            return i; // Return the index if key is found
                        }
                    }
                    return -1; // Return -1 if key is not found
                }
                
                public static void main(String[] args) {
                    int[] arr = {1, 2, 3, 4, 5};
                    int key = 3;
                    int result = linearSearch(arr, key);
                    if (result != -1) {
                        System.out.println("Element " + key + " found at index " + result);
                    } else {
                        System.out.println("Element " + key + " not found");
                    }
                }
            }
        """.trimIndent()
    ),
    TutorialContent.Implementation(
        languageName = "Python",
        code = """
            def linear_search(arr, key):
                for i in range(len(arr)):
                    if arr[i] == key:
                        return i  # Return the index if key is found
                return -1  # Return -1 if key is not found
            
            arr = [1, 2, 3, 4, 5]
            key = 3
            result = linear_search(arr, key)
            if result != -1:
                print(f"Element {key} found at index {result}")
            else:
                print(f"Element {key} not found")
        """.trimIndent()
    ),
    TutorialContent.Implementation(
        languageName = "Kotlin",
        code = """
            fun linearSearch(arr: IntArray, key: Int): Int {
                for (i in arr.indices) {
                    if (arr[i] == key) {
                        return i  // Return the index if key is found
                    }
                }
                return -1  // Return -1 if key is not found
            }
            
            fun main() {
                val arr = intArrayOf(1, 2, 3, 4, 5)
                val key = 3
                val result = linearSearch(arr, key)
                if (result != -1) {
                    println("Element $ key found at index $ result")
                } else {
                    println("Element $ key not found result")
                }
            }
        """.trimIndent()
    )
)
