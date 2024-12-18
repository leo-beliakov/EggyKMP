package com.leoapps.eggy.timer

import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base.egg.domain.model.EggSize
import com.leoapps.base.egg.domain.model.EggTemperature
import com.leoapps.eggy.progress.domain.model.TimerStatusUpdate
import kotlinx.coroutines.flow.Flow

interface TimerManager {
    val timerUpdates: Flow<TimerStatusUpdate>

    suspend fun isTimerScheduled(): Boolean
    fun cancelTimer()
    fun startTimer(
        eggType: EggBoilingType,
        eggSize: EggSize,
        eggTemperature: EggTemperature,
        boilingTime: Long,
    )

    fun onAppRelaunched()
    fun onAppLaunchedFromNotification()
    fun onAppLaunched()
}