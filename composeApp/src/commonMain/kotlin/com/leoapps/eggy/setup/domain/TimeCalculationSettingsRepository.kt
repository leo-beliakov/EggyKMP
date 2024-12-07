package com.leoapps.eggy.setup.domain

interface TimeCalculationSettingsRepository {
    suspend fun shouldUseFakeTime(): Boolean
    suspend fun setShouldUseFakeTime(shouldUseFakeTime: Boolean)
}