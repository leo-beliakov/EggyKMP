package com.leoapps.eggy.root.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoapps.eggy.timer.TimerManager
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
        // todo
        //  hasBeenOpenedFromNotification should be based on whether the user clicked the actual
        //  timer finish notification.


        viewModelScope.launch {
            when {
                timerManager.isTimerScheduled() -> {
                    timerManager.onAppRelaunched()
                    _navCommands.send(RootNavigationCommand.OpenSetupScreen) //todo change

                }

                hasBeenOpenedFromNotification -> {
                    timerManager.onAppLaunchedFromNotification()
                    _navCommands.send(RootNavigationCommand.OpenSetupScreen)
                }

                else -> {
                    timerManager.onAppLaunched()
                }
            }
        }
    }
}
