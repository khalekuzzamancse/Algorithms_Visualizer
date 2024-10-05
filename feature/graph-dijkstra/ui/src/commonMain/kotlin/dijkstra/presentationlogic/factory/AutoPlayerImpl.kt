package mst.presentationlogic.factory

import mst.presentationlogic.controller.Controller
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AutoPlayerImpl(
    override var onNextCallback: () -> Unit={}
) : Controller.AutoPlayer {
    private val _delay = MutableStateFlow<Int?>(null)
    private var scope: CoroutineScope? = null

    init {
        observeAutoPlay()
    }

    private fun observeAutoPlay() {
        scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        scope?.launch {
            _delay.collect { delay ->
                if (delay != null && delay > 0) {
                    while (isActive) {
                        delay(delay*1L)
                        if (isActive) {
                            onNextCallback()
                        }
                    }
                }
            }
        }
    }



    override fun isAutoPlayMode() = (_delay.value != null)

    override fun autoPlayRequest(delay: Int) {
        val isScopeInactiveOrNull=scope == null || scope?.isActive == false
        if (isScopeInactiveOrNull) {
            observeAutoPlay()
        }
        _delay.update { delay }
    }

    override fun dismiss() {
        _delay.update { null }
        cancelAutoPlay()
    }

    private fun cancelAutoPlay() {
        scope?.cancel()
        scope = null
    }
}
