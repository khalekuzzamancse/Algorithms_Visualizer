package core_ui.array.dynamic_array

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import core_ui.array.array.controller.BoundingRectangle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


data class DynamicElement(
    val label: String,
    private val _size: MutableState<Dp>,
    val sizePx:Float =100f,
    private val _offset: MutableState<Offset> = mutableStateOf(Offset.Zero),
    private val _color: MutableState<Color> = mutableStateOf(Color.Red),
    private val _clickable: MutableState<Boolean> = mutableStateOf(false),
    private val _draggable: MutableState<Boolean> = mutableStateOf(true),
    private val isHideBorder: MutableState<Boolean> = mutableStateOf(true),
    private val _boundingRectangleColor: MutableState<Color> = mutableStateOf(Color.Unspecified)
) {
    private val _currentPosition: MutableState<Offset> = mutableStateOf(Offset.Zero)
    val currentPosition: Offset
        get() = _currentPosition.value

    fun onPositionChanged(offset: Offset) {
        _currentPosition.value = offset

    }
    fun changeOffset(offset: Offset) {
        _offset.value+=offset
    }

    val bottomLeft: Offset
        get() = Offset(_currentPosition.value.x, _currentPosition.value.y+sizePx)
    val size: Dp
        get() = _size.value
    val backgroundColor: Color
        get() = _boundingRectangleColor.value
    val topLeft: Offset
        get() = _offset.value
    val center: Offset
        get() = _offset.value + Offset(sizePx / 2, sizePx / 2)
    private val bottomRight: Offset
        get() = _offset.value + Offset(sizePx, sizePx)
    val boundingRectangle: BoundingRectangle
        get() = BoundingRectangle(topLeft, bottomRight)



    private var blinkingJob: Job? = null
    private var blinkingBackgroundJob: Job? = null
    private var isBlinkingBackground by mutableStateOf(false)
    private var isBlinking by mutableStateOf(false)

    private var originalColor by mutableStateOf(Color.Red)


    val color: Color
        get() = _color.value
    val clickable: Boolean
        get() = _clickable.value
    val draggable: Boolean
        get() = _draggable.value
    private var _dragStartFrom: Offset = _offset.value
    val dragStartFrom: Offset
        get() = _dragStartFrom


    fun enableDrag() {
        _draggable.value = true
    }

    val shouldShowBorder: Boolean
        get() = !isHideBorder.value

    fun hideBorder() {
        isHideBorder.value = true
    }

    fun changeBoundingRectColor(color: Color) {
        _boundingRectangleColor.value = color
    }

    fun showBorder() {
        isHideBorder.value = false
    }


    fun enableClick() {
        _clickable.value = true
    }

    fun disableDrag() {
        _draggable.value = false
    }

    fun disableClick() {
        _clickable.value = false
    }

    fun onDrag(dragAmount: Offset) {
        if (_draggable.value) {
            _offset.value += dragAmount
        }
    }

    fun onDragStart(offset: Offset) {
        _dragStartFrom = offset
    }

    fun onDragEnd() {

    }

    fun changeColor(color: Color) {
        this._color.value = color
    }

    fun moveAt(offset: Offset) {
        _offset.value = offset
    }

    fun resetColor() {
        _color.value = Color.Red
    }

    fun resetOffset() {
        _offset.value = Offset.Zero
    }

    fun moveAtInfinite() {
        _offset.value = Offset.Infinite
    }

    fun onClick() {

    }

    fun blink() {
        if (!isBlinking) {
            isBlinking = true
            originalColor = _color.value
            blinkingJob = CoroutineScope(Dispatchers.IO).launch {
                while (isBlinking) {
                    _color.value = randomColor()
                    delay(500)
                }
            }
        }
    }

    fun blink(duration: Long) {
        if (!isBlinking) {
            isBlinking = true
            originalColor = _color.value
            var till = 0L
            blinkingJob = CoroutineScope(Dispatchers.IO).launch {
                while (till < duration) {
                    _color.value = randomColor()
                    delay(500)
                    till += 500
                }
            }
        }
    }

    fun stopBlink() {
        isBlinking = false
        blinkingJob?.cancel()
        _color.value = originalColor // Restore the original color
    }

    fun blinkBackground() {

        if (!isBlinkingBackground) {
            isBlinkingBackground = true
            blinkingBackgroundJob = CoroutineScope(Dispatchers.IO).launch {
                while (isBlinkingBackground) {
                    _boundingRectangleColor.value = randomColor()
                    delay(500)
                }
            }
        }
    }

    fun blinkBackground(
        duration: Long,
        scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    ) {
        if (!isBlinkingBackground) {
            isBlinkingBackground = true
            var till = 0L
            blinkingBackgroundJob = scope.launch {
                while (till < duration) {
                    _boundingRectangleColor.value = randomColor()
                    delay(200)
                    till += 200
                }
                isBlinkingBackground = false
            }
        }
    }

    fun stopBlinkBackground() {
        isBlinkingBackground = false
        blinkingBackgroundJob?.cancel()
        _boundingRectangleColor.value = Color.Unspecified
    }


    private fun randomColor(): Color {
        val red = Random.nextFloat()
        val green = Random.nextFloat()
        val blue = Random.nextFloat()
        return Color(red, green, blue)
    }

}

