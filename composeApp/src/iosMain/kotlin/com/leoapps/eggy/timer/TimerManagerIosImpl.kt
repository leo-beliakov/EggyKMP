package com.leoapps.eggy.timer

import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.progress.domain.TimerSettingsRepository
import com.leoapps.eggy.progress.domain.model.TimerStatusUpdate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.time.DurationUnit
import kotlin.time.toDuration


private const val TIMER_UPDATE_INTERVAL = 200L
private const val NOTIFICATION_UPDATE_INTERVAL = 1000L

class TimerManagerIosImpl(
    private val liveActivityManager: LiveActivityManager,
    private val notificationsManager: NotificationsManager,
    private val timerSettingsRepository: TimerSettingsRepository,
) : TimerManager {

    private val coroutineScope = CoroutineScope(Job())

    private var timer: CountDownTimer? = null

    private val _timerUpdates = MutableSharedFlow<TimerStatusUpdate>()
    override val timerUpdates = _timerUpdates.asSharedFlow()

    // There're four possible options for a running timer:
    // 1. There's an active LiveActivity && it has not run out of time yet
    // 2. There's an active CountDownTimer
    // 3. There's a pending notification
    // 4. Combinations of the above
    override suspend fun isTimerScheduled(): Boolean {
        return notificationsManager.hasScheduledNotification() ||
                (liveActivityManager.isRunning && isTimerEndTimeInTheFuture()) ||
                timer?.isRunning == true
    }

    override fun cancelTimer() {
        coroutineScope.launch {
            timer?.cancel()
            timerSettingsRepository.clearTimerSettings()
            notificationsManager.cancelCompleteNotification()
            liveActivityManager.stopLiveActivity()
            _timerUpdates.emit(TimerStatusUpdate.Canceled)
        }
    }

    override fun startTimer(boilingTime: Long, eggType: EggBoilingType) {
        coroutineScope.launch {
            val timerEndTime = Clock.System.now() + boilingTime.toDuration(DurationUnit.MILLISECONDS)
            timerSettingsRepository.saveTimerSettings(timerEndTime, eggType)
            notificationsManager.scheduleCompleteNotification(boilingTime)
            liveActivityManager.startLiveActivity(boilingTime)

            timer = CountDownTimer(
                millisInFuture = boilingTime,
                tickInterval = TIMER_UPDATE_INTERVAL,
                onTick = { millisUntilFinished ->
                    coroutineScope.launch {
                        _timerUpdates.emit(
                            TimerStatusUpdate.Progress(
                                timePassedMs = boilingTime - millisUntilFinished
                            )
                        )
                    }
                },
                onTimerFinished = {
                    coroutineScope.launch {
                        timerSettingsRepository.clearTimerSettings()
                        _timerUpdates.emit(TimerStatusUpdate.Finished)
                    }
                },
            )
            timer?.start()
        }
    }

    override fun onAppRelaunched() {
        coroutineScope.launch {
            val timerSettings = timerSettingsRepository.getTimerSettings()  ?: return@launch
            val boilingTime = timerSettings.timerEndTime - Clock.System.now()
            startTimer(boilingTime.inWholeMilliseconds, timerSettings.eggType)
        }
    }

    override fun onAppLaunchedFromNotification() {
        coroutineScope.launch {
            timerSettingsRepository.clearTimerSettings()
            liveActivityManager.stopLiveActivity()
        }
    }

    override fun onAppLaunched() {
        coroutineScope.launch {
            timerSettingsRepository.clearTimerSettings()
            liveActivityManager.stopLiveActivity()
        }
    }

    private suspend fun isTimerEndTimeInTheFuture(): Boolean {
        val timerEndTime = timerSettingsRepository.getTimerSettings()?.timerEndTime ?: return false
        return timerEndTime > Clock.System.now()
    }
}

