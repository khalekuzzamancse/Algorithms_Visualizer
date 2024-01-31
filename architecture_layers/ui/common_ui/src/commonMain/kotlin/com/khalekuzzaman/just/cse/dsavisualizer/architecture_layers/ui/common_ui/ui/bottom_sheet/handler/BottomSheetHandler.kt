package com.just.cse.digital_diary.two_zero_two_three.common_ui.bottom_sheet.handler

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class BottomSheetHandlerImp(
    private val scope: CoroutineScope= CoroutineScope(Dispatchers.Default),
    initialState:SheetValue=SheetValue.Hidden,
): BottomSheetHandler {
    private val _sheetState = MutableStateFlow(
        SheetState(
            initialValue = initialState,
            confirmValueChange = { true },
            skipPartiallyExpanded = false
        )
    )
    override val sheetState = _sheetState.asStateFlow()
    init {
        scope.launch {
            delay(1500)
            _sheetState.update {
                SheetState(
                    initialValue = SheetValue.PartiallyExpanded,
                    confirmValueChange = { true },
                    skipPartiallyExpanded = false
                )
            }
        }
    }

    override fun hideSheet() {
       _sheetState.update {
           SheetState(
               initialValue = SheetValue.PartiallyExpanded,
               confirmValueChange = { true },
               skipPartiallyExpanded = false
           )
       }
    }
    override fun expand() {
        _sheetState.update {
            SheetState(
                initialValue = SheetValue.Expanded,
                confirmValueChange = { true },
                skipPartiallyExpanded = false
            )
        }
    }
    override fun partialExpand() {
        _sheetState.update {
            SheetState(
                initialValue = SheetValue.PartiallyExpanded,
                confirmValueChange = { true },
                skipPartiallyExpanded = false
            )
        }
    }
    override fun toggleState(){
        scope.launch {
            when (_sheetState.value.currentValue) {
                SheetValue.Hidden -> {
                    _sheetState.update {
                        SheetState(
                            initialValue = SheetValue.PartiallyExpanded,
                            confirmValueChange = { true },
                            skipPartiallyExpanded = false
                        )
                    }
                }
                SheetValue.PartiallyExpanded -> {
                    _sheetState.update {
                        SheetState(
                            initialValue = SheetValue.Expanded,
                            confirmValueChange = { true },
                            skipPartiallyExpanded = false
                        )
                    }
                }

                SheetValue.Expanded -> {
                    _sheetState.update {
                        SheetState(
                            initialValue = SheetValue.Hidden,
                            confirmValueChange = { true },
                            skipPartiallyExpanded = false
                        )
                    }
                }
            }

        }
    }



}

@OptIn(ExperimentalMaterial3Api::class)
interface BottomSheetHandler {
    val sheetState: StateFlow<SheetState>
    fun hideSheet()
    fun expand()
    fun partialExpand()
    fun toggleState()
}
