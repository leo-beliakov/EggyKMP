package com.leoapps.eggy.base.egg.domain

import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.progress.domain.model.TimerStatusUpdate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TIMER_UPDATE_INTERVAL = 200L

class TimerInteractor(
    private val timerPlatformHelper: TimerPlatformHelper,
) {
    private var timer: CountDownTimer? = null

    private val _timerUpdates = MutableStateFlow<TimerStatusUpdate>(TimerStatusUpdate.Idle)
    val timerUpdates = _timerUpdates.asStateFlow()

    fun isTimerRunning() = timerPlatformHelper.isTimerRunning()

    fun getTimerSpecs() = timerPlatformHelper.getTimerSpecs()

    fun startTimer(boilingTime: Long, eggType: EggBoilingType) {
        timer?.cancel()
        timer = CountDownTimer(
            millisInFuture = boilingTime, // 45 sec for testing
            tickInterval = TIMER_UPDATE_INTERVAL,
            onTick = ::updateProgress,
            onTimerFinished = ::onTimerFinished,
            onTimerStrated = {
                _timerUpdates.value = TimerStatusUpdate.Progress(0)
                timerPlatformHelper.onStartTimer()
            },
        )
        timer?.start()
    }

    fun stopTimer() {
        timer?.cancel()
        timerPlatformHelper.onStopTimer()
        _timerUpdates.value = TimerStatusUpdate.Canceled
    }

    private fun updateProgress(millisPassed: Long) {
        println("MyTag, updateProgress: $millisPassed")
        _timerUpdates.value = TimerStatusUpdate.Progress(millisPassed)
        timerPlatformHelper.onTimerProgress(millisPassed)
    }

    private fun onTimerFinished() {
        println("MyTag, onTimerFinished")
        timerPlatformHelper.onFinishTimer()
        _timerUpdates.value = TimerStatusUpdate.Finished
    }
}
