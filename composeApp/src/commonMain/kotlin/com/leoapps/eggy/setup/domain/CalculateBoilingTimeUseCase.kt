package com.leoapps.setup.domain

import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base.egg.domain.model.EggSize
import com.leoapps.base.egg.domain.model.EggTemperature
import com.leoapps.eggy.setup.domain.TimeCalculationSettingsRepository

class CalculateBoilingTimeUseCase(
    private val settingsRepository: TimeCalculationSettingsRepository
) {

    operator suspend fun invoke(
        temperature: EggTemperature?,
        size: EggSize?,
        type: EggBoilingType?,
    ): Long {
        return if (temperature != null && size != null && type != null) {
            if (settingsRepository.shouldUseFakeTime()) {
                25_000L
            } else {
                calculateBoilingTime(temperature, size, type)
            }
        } else {
            0L
        }
    }

    private fun calculateBoilingTime(
        temperature: EggTemperature,
        size: EggSize,
        type: EggBoilingType,
    ): Long {
        // Define the base boiling times in seconds
        val baseTime = when (type) {
            EggBoilingType.SOFT -> 240L
            EggBoilingType.MEDIUM -> 360L
            EggBoilingType.HARD -> 480L
        }

        // Adjust the time based on the egg size
        val sizeAdjustment = when (size) {
            EggSize.SMALL -> -30L
            EggSize.MEDIUM -> 0L
            EggSize.LARGE -> 30L
        }

        // Adjust the time based on the egg temperature
        val temperatureAdjustment = when (temperature) {
            EggTemperature.ROOM -> 0L
            EggTemperature.FRIDGE -> 60L
        }

        val totalTimeInSeconds = baseTime + sizeAdjustment + temperatureAdjustment
        return totalTimeInSeconds * 1000
    }
}