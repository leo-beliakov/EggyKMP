package com.leoapps.vibration.presentation

import androidx.compose.runtime.compositionLocalOf
import com.leoapps.eggy.common.vibration.domain.VibrationManager

val LocalVibrationManager = compositionLocalOf<VibrationManager> {
    error("No WaterAppVibrator provided")
}