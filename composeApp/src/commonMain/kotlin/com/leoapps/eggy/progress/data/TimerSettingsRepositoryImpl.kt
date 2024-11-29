package com.leoapps.eggy.progress.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.progress.domain.TimerSettingsRepository
import com.leoapps.eggy.progress.domain.model.TimerSettings
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Instant

class TimerSettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): TimerSettingsRepository {

    private val timerEndTimeKey: Preferences.Key<Long>
        get() = longPreferencesKey(TIMER_END_TIME_KEY)

    private val timerEggTypeKey: Preferences.Key<String>
        get() = stringPreferencesKey(TIMER_EGG_TYPE_KEY)

    override suspend fun saveTimerSettings(timerEndTime: Instant, eggType: EggBoilingType) {
        dataStore.edit { preferences ->
            preferences[timerEggTypeKey] = eggType.name
            preferences[timerEndTimeKey] = timerEndTime.toEpochMilliseconds()
        }
    }

    override suspend fun getTimerSettings(): TimerSettings? {
        val currentData = dataStore.data.first()
        val timerEndTimeMillis = currentData[timerEndTimeKey] ?: return null
        val storedEggTypeString = currentData[timerEggTypeKey] ?: return null
        val storedEggType = EggBoilingType.fromString(storedEggTypeString) ?: return null

        return TimerSettings(
            timerEndTime = Instant.fromEpochMilliseconds(timerEndTimeMillis),
            eggType =  storedEggType,
        )
    }

    override suspend fun clearTimerSettings() {
        dataStore.edit { preferences ->
            preferences.remove(timerEndTimeKey)
            preferences.remove(timerEggTypeKey)
        }
    }

    private companion object {
        const val TIMER_END_TIME_KEY = "TIMER_BOILING_TIME_KEY"
        const val TIMER_EGG_TYPE_KEY = "TIMER_EGG_TYPE_KEY"
    }
}