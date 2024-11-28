package com.leoapps.eggy.timer

import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter

private val COMPLETE_NOTIFICATION_ID = "timerCompleted"

class NotificationsManager {

    fun scheduleCompleteNotification(duration: Long) {
        val content = UNMutableNotificationContent().apply {
            setTitle("Timer Completed")
            setBody("Your timer has finished!")
            setSound(UNNotificationSound.defaultSound())
        }

        val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
            timeInterval = duration / 1000.0, // convert Ms to Sec
            repeats = false
        )
        val request = UNNotificationRequest.Companion.requestWithIdentifier(
            identifier = COMPLETE_NOTIFICATION_ID,
            content = content,
            trigger = trigger
        )

        val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
        notificationCenter.addNotificationRequest(request) { error ->
            if (error != null) {
                println("Failed to schedule notification: ${error.localizedDescription}")
            } else {
                println("Notification scheduled successfully.")
            }
        }
    }

    fun cancelCompleteNotification() {
        val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
        notificationCenter.removePendingNotificationRequestsWithIdentifiers(
            listOf(COMPLETE_NOTIFICATION_ID)
        )
    }
}