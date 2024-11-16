package com.leoapps.eggy.vibration.domain

interface VibrationManager {
    fun vibrateOnClick()
    fun vibratePattern(pattern: LongArray)
}