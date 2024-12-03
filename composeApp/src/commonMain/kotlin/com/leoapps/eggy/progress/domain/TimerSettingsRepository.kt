package com.leoapps.eggy.progress.domain

import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base.egg.domain.model.EggSize
import com.leoapps.base.egg.domain.model.EggTemperature
import com.leoapps.eggy.progress.domain.model.TimerSettings
import kotlinx.datetime.Instant

interface TimerSettingsRepository {

    suspend fun clearTimerSettings()
    suspend fun getTimerSettings(): TimerSettings?
    suspend fun saveTimerSettings(
        timerEndTime: Instant,
        timerTotalTime: Long,
        eggType: EggBoilingType,
        eggSize: EggSize,
        eggTemperature: EggTemperature,
    )
}