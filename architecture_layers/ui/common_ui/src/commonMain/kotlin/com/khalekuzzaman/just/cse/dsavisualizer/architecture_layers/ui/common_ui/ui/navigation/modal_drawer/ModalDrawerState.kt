package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.navigation.modal_drawer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ModalDrawerState(
    private val scope: CoroutineScope,
) {
    val drawerState = MutableStateFlow(DrawerState(DrawerValue.Closed))
    private val _drawerOpened=MutableStateFlow(false)
    val drawerOpened=_drawerOpened.asStateFlow()
    init {

        scope.launch {
            openDrawer()
            delay(2000)
            closeDrawer()
        }
    }


    fun openDrawer() {
        scope.launch {
            drawerState.value =DrawerState(DrawerValue.Open)
            _drawerOpened.value=true

        }
    }

    fun closeDrawer() {
        scope.launch {
            drawerState.value =DrawerState(DrawerValue.Closed)
            _drawerOpened.value=false
        }
    }
}
