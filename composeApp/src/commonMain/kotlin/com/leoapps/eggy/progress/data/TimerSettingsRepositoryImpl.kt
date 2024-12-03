package com.leoapps.eggy.progress.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base.egg.domain.model.EggSize
import com.leoapps.base.egg.domain.model.EggTemperature
import com.leoapps.eggy.progress.domain.TimerSettingsRepository
import com.leoapps.eggy.progress.domain.model.TimerSettings
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Instant

class TimerSettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : TimerSettingsRepository {

    private val timerEndTimeKey = longPreferencesKey("TIMER_END_TIME_KEY")
    private val timerTotalTimeKey = longPreferencesKey("TIMER_TOTAL_TIME_KEY")
    private val timerEggTypeKey = stringPreferencesKey("TIMER_EGG_TYPE_KEY")
    private val timerEggSizeKey = stringPreferencesKey("TIMER_EGG_SIZE_KEY")
    private val timerEggTempKey = stringPreferencesKey("TIMER_EGG_TEMP_KEY")

    override suspend fun saveTimerSettings(
        timerEndTime: Instant,
        timerTotalTime: Long,
        eggType: EggBoilingType,
        eggSize: EggSize,
        eggTemperature: EggTemperature,
    ) {
        dataStore.edit { preferences ->
            preferences[timerEndTimeKey] = timerEndTime.toEpochMilliseconds()
            preferences[timerTotalTimeKey] = timerTotalTime
            preferences[timerEggTypeKey] = eggType.toString()
            preferences[timerEggSizeKey] = eggSize.toString()
            preferences[timerEggTempKey] = eggTemperature.toString()
        }
    }

    override suspend fun getTimerSettings(): TimerSettings? {
        val currentData = dataStore.data.first()
        val timerEndTimeMillis = currentData[timerEndTimeKey] ?: return null
        val storedEggTypeString = currentData[timerEggTypeKey] ?: return null
        val storedEggSizeString = currentData[timerEggSizeKey] ?: return null
        val storedEggTempString = currentData[timerEggTempKey] ?: return null

        return TimerSettings(
            timerEndTime = Instant.fromEpochMilliseconds(timerEndTimeMillis),
            timerTotalTime = currentData[timerTotalTimeKey] ?: return null,
            eggType = EggBoilingType.valueOf(storedEggTypeString),
            eggSize = EggSize.valueOf(storedEggSizeString),
            eggTemperature = EggTemperature.valueOf(storedEggTempString),
        )
    }

    override suspend fun clearTimerSettings() {
        dataStore.edit { preferences ->
            preferences.remove(timerEndTimeKey)
            preferences.remove(timerTotalTimeKey)
            preferences.remove(timerEggTypeKey)
            preferences.remove(timerEggSizeKey)
            preferences.remove(timerEggTempKey)
        }
    }
}