package com.leoapps.eggy.root.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoapps.eggy.progress.domain.TimerSettingsRepository
import com.leoapps.eggy.root.presentation.model.RootNavigationCommand
import com.leoapps.eggy.timer.TimerManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class RootViewModel(
    private val timerManager: TimerManager,
    private val timerSettingsRepository: TimerSettingsRepository,
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

                    val timerSettings = timerSettingsRepository.getTimerSettings() ?: return@launch
                    _navCommands.send(
                        RootNavigationCommand.OpenProgressScreen(
                            boilingTime = timerSettings.timerTotalTime,
                            eggType = timerSettings.eggType
                        )
                    )
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
