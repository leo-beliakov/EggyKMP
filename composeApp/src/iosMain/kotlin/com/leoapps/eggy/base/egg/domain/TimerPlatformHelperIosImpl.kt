package com.leoapps.eggy.base.egg.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TimerPlatformHelperIosImpl : TimerPlatformHelper {
    override val timerUpdates: Flow<Int> = flowOf()

    override fun isTimerRunning(): Boolean = true

    override fun getTimerSpecs(): Int? = 0

    override fun onStartTimer() {
    }

    override fun onStopTimer() {
    }

    override fun onFinishTimer() {
    }

    override fun onTimerProgress(progress: Long) {
    }
}