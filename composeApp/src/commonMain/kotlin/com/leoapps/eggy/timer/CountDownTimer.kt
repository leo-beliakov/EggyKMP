package com.leoapps.eggy.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class CountDownTimer(
    private val millisInFuture: Long,
    private val tickInterval: Long,
    private val onTick: (Long) -> Unit,
    private val onTimerFinished: () -> Unit,
) {
    private val coroutineScope = CoroutineScope(Job())
    private var timerJob: Job? = null

    val isRunning: Boolean
        get() = timerJob?.isActive == true

    fun start() {
        timerJob?.cancel()
            val startTimeMillis = Clock.System.now().toEpochMilliseconds()
        timerJob = coroutineScope.launch {
            var millisPassed = 0L
            while (millisPassed < millisInFuture) {
                onTick(millisPassed)
                millisPassed = Clock.System.now().toEpochMilliseconds() - startTimeMillis
                delay(tickInterval - (millisPassed % tickInterval))
            }
            onTimerFinished()
        }
    }

    fun cancel() {
        timerJob?.cancel()
    }
}