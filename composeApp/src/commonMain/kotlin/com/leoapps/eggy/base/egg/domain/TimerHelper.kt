package com.leoapps.eggy.base.egg.domain

import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.progress.domain.model.TimerStatusUpdate
import kotlinx.coroutines.flow.Flow

interface TimerHelper {
    val timerUpdates: Flow<TimerStatusUpdate>

    fun isTimerRunning(): Boolean
    fun getTimerSpecs(): Int?

    fun stopTimer()
    fun startTimer(
        boilingTime: Long,
        eggType: EggBoilingType
    )
}