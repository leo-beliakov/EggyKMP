package com.leoapps.eggy.root.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoapps.eggy.base.egg.domain.TimerHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RootViewModel(
    private val timerHelper: TimerHelper
) : ViewModel() {

    private val _navCommands = MutableSharedFlow<Unit>() // todo replace with sealed class
    val navCommands = _navCommands.asSharedFlow()

    init {
        println("MyTag, ${timerHelper.isTimerRunning()}")
        if(timerHelper.isTimerRunning()) {
            val specs = timerHelper.getTimerSpecs() // todo use specs
            viewModelScope.launch {
                _navCommands.emit(Unit)
            }
        }
    }
}