package com.leoapps.eggy.base.egg.domain

import android.content.Context
import android.content.Intent
import com.leoapps.eggy.timer.TimerService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TimerPlatformHelperAndroidImpl(
    private val context: Context
) : TimerPlatformHelper {
    override val timerUpdates: Flow<Int> = flowOf()

    override fun isTimerRunning(): Boolean = TimerService.isRunning

    override fun getTimerSpecs(): Int? = 0

    override fun onStartTimer() {
        val intent = Intent(context, TimerService::class.java)
        context.startService(intent)
    }

    override fun onStopTimer() {
        val intent = Intent(context, TimerService::class.java)
        context.stopService(intent)
    }

    override fun onFinishTimer() {
    }

    override fun onTimerProgress(progress: Long) {
    }
}