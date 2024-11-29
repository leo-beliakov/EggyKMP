package com.leoapps.eggy.progress.domain

import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.progress.domain.model.TimerSettings
import kotlinx.datetime.Instant

interface TimerSettingsRepository {

    suspend fun saveTimerSettings(
        timerEndTime: Instant,
        eggType: EggBoilingType,
    )

    suspend fun getTimerSettings(): TimerSettings?

    suspend fun clearTimerSettings()
}