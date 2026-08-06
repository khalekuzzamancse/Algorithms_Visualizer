import SwiftUI

import SwiftUI

fileprivate struct InputRequest: Identifiable {
    let id = UUID()
    let route: Routes
}

public struct RootNavHost: View {
    @StateObject private var navController = NavController()

    @State private var inputRequest: InputRequest?

    @State private var inputArray: [Int] = []
    @State private var inputTarget: Int = 0

    public init() {}

    public var body: some View {
        NavigationStack(path: $navController.navStack) {
            HomeScreen { route in
                inputRequest = InputRequest(route: route)
            }
            .sheet(item: $inputRequest) { request in
                inputDialog(for: request.route)
            }
            .navigationDestination(for: Routes.self) { route in
                destination(for: route)
            }
        }
    }

    @ViewBuilder
    private func inputDialog(for route: Routes) -> some View {
        switch route {

        // Array and target
        case .LinearSearch, .BinarySearch:
            AlgorithmInputDialog { array, target in
                inputArray = array
                inputTarget = target

                closeDialogAndNavigate(to: route)
            }

        // Array only
        case .BubbleSort,
             .SelectionSort,
             .InsertionSort,
             .QuickSort:
            AlgorithmInputDialog { array in
                inputArray = array

                closeDialogAndNavigate(to: route)
            }
        }
    }

    @ViewBuilder
    private func destination(for route: Routes) -> some View {
        switch route {
        case .LinearSearch:
            LinearSearchRoute(
                array: inputArray,
                target: inputTarget
            )

        case .BinarySearch:
            BinarySearchRoute(
                array: inputArray.sorted(),
                target: inputTarget
            )

        case .BubbleSort:
            BubbleSortRoute(
                array: inputArray
            )

        case .SelectionSort:
            SelectionSortRoute(
                array: inputArray
            )

        case .InsertionSort:
            InsertionSortRoute(
                array: inputArray
            )

        case .QuickSort:
            QuickSortRoute(
                array: inputArray
            )
        }
    }

    private func closeDialogAndNavigate(to route: Routes) {
        inputRequest = nil

        DispatchQueue.main.async {
            navController.navigate(route)
        }
    }
}
class NavController :ObservableObject{
    @Published var navStack = NavigationPath()
    func navigate(_ destination: Routes) { navStack.append(destination)}
    func pop() { navStack.removeLast()}
    func navigateToRoot() { navStack.removeLast(navStack.count)}
}


