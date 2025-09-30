package lineards._core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * This layer should not depend on on any libraries or UI implementations such as GraphViewController
 * that is why exposing some event from here
 */

interface AutoPlayer {
    fun isAutoPlayMode(): Boolean
    fun autoPlayRequest(delay: Int)

    /**Must dismiss it if no longer required because it has coroutines run,we have to stop it*/
    fun dismiss()
    var onNextCallback: () -> Unit
    companion object{
        fun create()=AutoPlayerImpl.create()
    }
}


internal class AutoPlayerImpl private constructor(
    override var onNextCallback: () -> Unit = {}
) : AutoPlayer {
    private val _delay = MutableStateFlow<Int?>(null)
    private var scope: CoroutineScope? = null

    companion object{
        fun create():AutoPlayer=AutoPlayerImpl()
    }

    init {
        observeAutoPlay()
    }

    private fun observeAutoPlay() {
        scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        scope?.launch {
            _delay.collect { delay ->
                if (delay != null && delay > 0) {
                    while (isActive) {
                        delay(delay * 1L)
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
        val isScopeInactiveOrNull = scope == null || scope?.isActive == false
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
