package com.leoapps.eggy.common.vibration

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.leoapps.eggy.common.vibration.domain.VibrationManager


class VibrationManagerAndroidImpl(
    val context: Context
) : VibrationManager {

    var vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun vibrateOnClick() {
        val effect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
        } else {
            VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
        }
        vibrator.vibrate(effect)
    }

    override fun vibrateCelebration() {
        vibrator.vibrate(
            VibrationEffect.createWaveform(
                TIMER_FINISH_VIBRARTION_PATTERN,
                -1 // means no repeat
            )
        )
    }

    private companion object {
        val TIMER_FINISH_VIBRARTION_PATTERN = longArrayOf(0, 200, 100, 300, 400, 500)
    }
}