package com.leoapps.eggy.common.vibrator

import com.leoapps.eggy.common.vibration.domain.VibrationManager
import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle
import platform.UIKit.UINotificationFeedbackGenerator
import platform.UIKit.UIUserNotificationType
import platform.darwin.DISPATCH_TIME_NOW
import platform.darwin.dispatch_after
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_t
import platform.darwin.dispatch_time

class VibrationManagerIosImpl : VibrationManager {

    private val notificationFeedbackGenerator = UINotificationFeedbackGenerator()
    private val impactFeedbackGenerator = UIImpactFeedbackGenerator(
        style = UIImpactFeedbackStyle.UIImpactFeedbackStyleMedium
    )

    override fun vibrateOnClick() {
        impactFeedbackGenerator.prepare()
        impactFeedbackGenerator.impactOccurred()
    }

    override fun vibratePattern(pattern: LongArray) {
        // iOS doesn't natively support custom vibration patterns.
        // Simulate a pattern using delays and the `kSystemSoundID_Vibrate` sound.
        simulateVibrationPattern(pattern)
    }

    private fun simulateVibrationPattern(pattern: LongArray) {
//        val queue: dispatch_queue_t = dispatch_get_main_queue()
//        pattern.forEachIndexed { index, duration ->
//            dispatch_after(
//                dispatch_time(DISPATCH_TIME_NOW, duration * 1_000_000),
//                queue
//            ) {
//                if (index % 2 == 0) {
//                    notificationFeedbackGenerator.notificationOccurred(
//                        when (index % 3) {
//                            0 -> UIUserNotificationType.UIUserUIUserNotificationTypeNone
//                            1 -> UIUserNotificationType.UIUserNotificationTypeAlert
//                            else -> UIUserNotificationType.UIUserNotificationTypeAll;
//                        }
//                    )
//                }
//            }
//        }
    }
}