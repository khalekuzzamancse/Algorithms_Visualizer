@file:Suppress("functionName", "propertyName", "unused")

package lineards._core

import core.ui.GlobalMessenger
import core.ui.core.SimulationScreenState
import core.ui.core.array.controller.VisualArrayController


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import lineards.linear_search.domain.service.PseudocodeGenerator

internal interface RouteController {
    val inputMode: StateFlow<Boolean>
    val arrayController: StateFlow<VisualArrayController?>
    val state: StateFlow<SimulationScreenState>
    val code: StateFlow<String?>
    val autoPlayer: AutoPlayer
    fun onListInputted(inputData: List<Int>)
    fun onNext()
    fun onReset()
}


internal interface SearchRouteController : RouteController {
    /**
     * *In case of sort algorithms just ignore or un-used it
     * In case of search call it before the list input or do not forget to call it
     * */
    fun onTargetInputted(target: Int)
}

internal interface SortRouteController : RouteController
internal abstract class BaseRouteController : RouteController {
    protected val _inputMode = MutableStateFlow(true)
    override val inputMode = _inputMode.asStateFlow()

    private val _array = MutableStateFlow(listOf(10, 5, 4, 13, 8))
    protected val list = _array.asStateFlow()

    protected val _code = MutableStateFlow<String?>(null)
    override val code = _code.asStateFlow()

    private val _state = MutableStateFlow(SimulationScreenState(showPseudocode = true))
    override val state = _state.asStateFlow()

    final override val autoPlayer = AutoPlayer.create()

    override val arrayController = MutableStateFlow<VisualArrayController?>(null)

    override fun onListInputted(inputData: List<Int>) {
        _inputMode.update { false }
        _array.update { inputData }
        arrayController.update { _createController() }
    }

    override fun onReset() {
        autoPlayer.dismiss()
        arrayController.update { _createController() }
        _code.update { PseudocodeGenerator.rawCode }
    }

    protected open fun _onFinished() {
        autoPlayer.dismiss()
        GlobalMessenger.updateAsEndedMessage()
    }

    protected abstract fun _createController(): VisualArrayController

    init {
        autoPlayer.onNextCallback = ::onNext
    }
}


internal abstract class SearchRouteControllerBase : BaseRouteController(), SearchRouteController {
    private val tag = this.javaClass.simpleName
    protected var target: Int = 0

    override fun onTargetInputted(target: Int) {
        this.target = target
    }
}

internal abstract class SortRouteControllerBase : BaseRouteController(), SortRouteController


