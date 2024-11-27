package com.leoapps.eggy.root.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoapps.eggy.base.egg.domain.TimerManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RootViewModel(
    private val timerManager: TimerManager
) : ViewModel() {

    private val _navCommands = MutableSharedFlow<Unit>() // todo replace with sealed class
    val navCommands = _navCommands.asSharedFlow()

    init {
        println("MyTag, ${timerManager.isTimerRunning()}")
        if(timerManager.isTimerRunning()) {
            val specs = timerManager.getTimerSpecs() // todo use specs
            viewModelScope.launch {
                _navCommands.emit(Unit)
            }
        }
    }
}