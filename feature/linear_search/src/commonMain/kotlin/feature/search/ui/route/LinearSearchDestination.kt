package feature.search.ui.route

import androidx.compose.runtime.Composable


@Composable
fun LinearSearchDestination() {
    VisualizationRoute()

//    val destinationViewModel = remember { DestinationViewModel() }
//    val selected = destinationViewModel.selectedDestinationIndex.collectAsState().value
//    var currentMode by remember { mutableStateOf(WindowMode.Compact) }
//    val navigationModifier =
//        if (currentMode == WindowMode.Compact) Modifier.fillMaxWidth() else Modifier.width(
//            IntrinsicSize.Max
//        )
//
//    TwoPaneLayout(
//        leftPane = {
//            VerticalListNavigation(
//                modifier = navigationModifier.fillMaxHeight().background(MaterialTheme.colorScheme.secondary),
//                destinations = destinationViewModel.sections.map {
//                    NavigationItemInfo(label = it, unFocusedIcon = Icons.Default.TextFormat)
//                },
//                onDestinationSelected = destinationViewModel::onDestinationSelected,
//                selectedDestinationIndex = selected,
//            )
//        },
//        topOrRightPane = {
//            Box(Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
//                when (selected) {
//                    0 -> {
//                        BackArrowDecorator(onBackRequest = destinationViewModel::onBackRequest) {
//                            TheorySection()
//                        }
//
//                    }
//                    1 -> {
//                        BackArrowDecorator(onBackRequest = destinationViewModel::onBackRequest) {
//                            StepsSection()
//                        }
//
//                    }
//                    2 -> {
//                        BackArrowDecorator(onBackRequest = destinationViewModel::onBackRequest) {
//                            Implementation()
//                        }
//
//                    }
//                    3 -> {
//                        BackArrowDecorator(onBackRequest = destinationViewModel::onBackRequest) {
//                            VisualizationRoute()
//                        }
//
//                    }
//                }
//            }
//
//        },
//        showTopOrRightPane = selected >= 0,
//        secondaryPaneAnimationState = selected,
//        onCurrentMode = {
//            currentMode = it
//        }
//    )


}


