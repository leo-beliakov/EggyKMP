package com.leoapps.eggy.base.egg.domain

import kotlinx.coroutines.flow.Flow

interface TimerPlatformHelper {
    val timerUpdates: Flow<Int>
    fun isTimerRunning(): Boolean
    fun getTimerSpecs(): Int?
    fun onStartTimer()
    fun onStopTimer()
    fun onFinishTimer()
    fun onTimerProgress(progress: Long)
}