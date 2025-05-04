@file:Suppress("functionName")

package lineards.core

import core.lang.Logger
import core_ui.GlobalMessenger
import core_ui.core.SimulationScreenState
import core_ui.core.array.controller.VisualArrayController
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
    fun onInputComplete(inputData: List<Int>, target: Int)
    fun onNext()
    fun onReset()
}

internal abstract class RouteControllerBase : RouteController {
    private val tag = this.javaClass.simpleName
    protected var target: Int = 0
    private val _inputMode = MutableStateFlow(true)
    override val inputMode = _inputMode.asStateFlow()
    private val _array = MutableStateFlow(listOf(10, 5, 4, 13, 8))
    protected val list = _array.asStateFlow()
    protected val _code = MutableStateFlow<String?>(null)
    override val code = _code.asStateFlow()
    private val _state = MutableStateFlow(SimulationScreenState(showPseudocode = true))
    override val state = _state.asStateFlow()
    final override val autoPlayer = AutoPlayer.create()
    override val arrayController = MutableStateFlow<VisualArrayController?>(null)


    override fun onInputComplete(inputData: List<Int>, target: Int) {
        this.target = target
        _array.update { inputData }
        _inputMode.update { false }
        arrayController.update { _createController() }
        Logger.on("$tag::onInputComplete", "${arrayController.value}")
    }

    init {
        autoPlayer.onNextCallback = ::onNext
    }

    protected abstract fun _createController(): VisualArrayController
    override fun onReset() {
        autoPlayer.dismiss()
        arrayController.update { _createController() }
        _code.update { PseudocodeGenerator.rawCode }
    }

    protected open fun _onFinished() {
        autoPlayer.dismiss()//clear if auto play
        GlobalMessenger.updateAsEndedMessage()
    }
}


