package com.leoapps.eggy.root.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoapps.eggy.base.egg.domain.TimerManager
import com.leoapps.eggy.root.presentation.model.RootNavigationCommand
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RootViewModel(
    val timerManager: TimerManager
) : ViewModel() {

    private val _navCommands = Channel<RootNavigationCommand>()
    val navCommands = _navCommands.receiveAsFlow()

    val hasBeenOpenedFromNotification = false //todo real impl

    init {
        viewModelScope.launch {
            when {
                timerManager.isTimerRunning() -> {
                    _navCommands.send(RootNavigationCommand.OpenSetupScreen) //todo change

                }

                hasBeenOpenedFromNotification -> {
                    _navCommands.send(RootNavigationCommand.OpenSetupScreen)
                }
            }
        }
    }
}
