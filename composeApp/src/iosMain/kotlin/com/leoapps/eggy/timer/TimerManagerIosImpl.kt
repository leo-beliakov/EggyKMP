package com.leoapps.eggy.timer

import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.base.egg.domain.CountDownTimer
import com.leoapps.eggy.base.egg.domain.TimerManager
import com.leoapps.eggy.progress.domain.model.TimerStatusUpdate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


private const val TIMER_UPDATE_INTERVAL = 200L
private const val NOTIFICATION_UPDATE_INTERVAL = 1000L

class TimerManagerIosImpl(
    private val liveActivityManager: LiveActivityManager,
    private val notificationsManager: NotificationsManager,
) : TimerManager {

    private val coroutineScope = CoroutineScope(Job())

    private var timer: CountDownTimer? = null

    private val _timerUpdates = MutableSharedFlow<TimerStatusUpdate>()
    override val timerUpdates = _timerUpdates.asSharedFlow()

    override fun isTimerRunning() = timer?.isRunning == true

    override fun getTimerSpecs(): Int? = 0 //todo

    override fun stopTimer() {
        timer?.cancel()
        notificationsManager.cancelCompleteNotification()
        liveActivityManager.stopLiveActivity()
        coroutineScope.launch {
            _timerUpdates.emit(TimerStatusUpdate.Canceled)
        }
    }

    override fun startTimer(boilingTime: Long, eggType: EggBoilingType) {
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
                    _timerUpdates.emit(TimerStatusUpdate.Finished)
                }
            },
        )
        timer?.start()
    }
}