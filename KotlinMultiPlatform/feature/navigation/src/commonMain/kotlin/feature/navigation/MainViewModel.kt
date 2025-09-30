package feature.navigation

import feature.navigation.drawer.Destination
import feature.navigation.drawer.NavigationDrawerController

class MainViewModel  {
    val controller = NavigationDrawerController()
    fun openDrawer() = controller.openDrawer()

    fun select(destination: Destination) = controller.select(destination)

}