package com.leoapps.eggy.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        timerJob = coroutineScope.launch {
            var millisPassed = 0L
            while (millisPassed < millisInFuture) {
                delay(tickInterval)

                onTick(millisPassed)

                millisPassed += tickInterval
            }
            onTimerFinished()
        }
    }

    fun cancel() {
        timerJob?.cancel()
    }
}