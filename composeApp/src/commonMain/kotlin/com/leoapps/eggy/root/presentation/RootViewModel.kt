package com.leoapps.eggy.root.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoapps.eggy.base.startup.AppStartupInformationProvider
import com.leoapps.eggy.logs.domain.EggyLogger
import com.leoapps.eggy.progress.domain.TimerSettingsRepository
import com.leoapps.eggy.root.presentation.model.RootNavigationCommand
import com.leoapps.eggy.timer.TimerManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RootViewModel(
    private val timerManager: TimerManager,
    private val timerSettingsRepository: TimerSettingsRepository,
    private val appStartupInformationProvider: AppStartupInformationProvider,
    private val logger: EggyLogger,
) : ViewModel() {

    private val _navCommands = Channel<RootNavigationCommand>()
    val navCommands = _navCommands.receiveAsFlow()

    init {
        logger.i { "RootViewModel init" }
        viewModelScope.launch {
            when {
                timerManager.isTimerScheduled() -> {
                    logger.i { "RootViewModel onAppRelaunched" }
                    timerManager.onAppRelaunched()

                    val timerSettings = timerSettingsRepository.getTimerSettings() ?: return@launch
                    _navCommands.send(
                        RootNavigationCommand.OpenProgressScreen(
                            boilingTime = timerSettings.timerTotalTime,
                            eggType = timerSettings.eggType
                        )
                    )
                }

                appStartupInformationProvider.isLaunchedFromNotification -> {
                    logger.i { "RootViewModel onAppLaunchedFromNotification" }
                    timerManager.onAppLaunchedFromNotification()
                    _navCommands.send(RootNavigationCommand.OpenSetupScreen)
                }

                else -> {
                    logger.i { "RootViewModel onAppLaunched" }
                    timerManager.onAppLaunched()
                }
            }
        }
    }
}
