package com.leoapps.eggy.common.vibrator

import com.leoapps.eggy.common.vibration.domain.VibrationManager
import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle
import platform.UIKit.UINotificationFeedbackGenerator
import platform.UIKit.UINotificationFeedbackType

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
        notificationFeedbackGenerator.prepare()
        notificationFeedbackGenerator.notificationOccurred(UINotificationFeedbackType.UINotificationFeedbackTypeError)
    }
}