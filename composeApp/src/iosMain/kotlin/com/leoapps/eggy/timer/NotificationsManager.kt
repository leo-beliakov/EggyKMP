package com.leoapps.eggy.timer

import kotlinx.coroutines.suspendCancellableCoroutine
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter
import kotlin.coroutines.resume

private val COMPLETE_NOTIFICATION_ID = "timerCompleted"

class NotificationsManager {

    suspend fun hasScheduledNotification(): Boolean {
        // iOS API is based on callbacks, that's why we need to use a continuation here
        return suspendCancellableCoroutine { continuation ->
            val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
            notificationCenter.getPendingNotificationRequestsWithCompletionHandler { requests ->
                val filteredRequests = requests?.filterIsInstance<UNNotificationRequest>()
                val isScheduled = filteredRequests?.any { it.identifier == COMPLETE_NOTIFICATION_ID } == true

                // Resume the coroutine with the result
                continuation.resume(isScheduled)
            }
        }
    }

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