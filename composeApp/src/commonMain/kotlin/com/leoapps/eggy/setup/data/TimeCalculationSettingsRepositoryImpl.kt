package com.leoapps.eggy.setup.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.leoapps.eggy.setup.domain.TimeCalculationSettingsRepository
import kotlinx.coroutines.flow.first

class TimeCalculationSettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : TimeCalculationSettingsRepository {

    private val shouldUseFakeTimeKey = booleanPreferencesKey("TIMER_FAKE_SETTINGS_KEY")

    override suspend fun shouldUseFakeTime(): Boolean {
        val currentData = dataStore.data.first()
        return currentData[shouldUseFakeTimeKey] ?: false
    }

    override suspend fun setShouldUseFakeTime(shouldUseFakeTime: Boolean) {
        dataStore.edit { preferences ->
            preferences[shouldUseFakeTimeKey] = shouldUseFakeTime
        }
    }
}