package com.leoapps.eggy.common.vibration.domain

interface VibrationManager {
    fun vibrateOnClick()
    fun vibratePattern(pattern: LongArray)
}