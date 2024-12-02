package com.leoapps.eggy.base.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.MainActivity
import com.leoapps.eggy.MainActivity.Companion.IS_LAUNCHED_FROM_NOTIFICATION_KEY
import com.leoapps.eggy.R
import com.leoapps.eggy.common.utils.convertMsToText
import com.leoapps.eggy.timer.TimerService

class BoilProgressNotificationManager(
    private val context: Context
) : NotificationChannelsManager {
    private val notificationManagerCompat = NotificationManagerCompat.from(context)

    override fun createChannels() {
        val notificationProgressChanel = NotificationChannel(
            PROGRESS_CHANNEL_ID,
            context.getString(R.string.notificaton_progress_channel_progress_title),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = context.getString(R.string.notificaton_progress_channel_progress_description)
            setShowBadge(false)
        }

        val notificationFinishChanel = NotificationChannel(
            FINISH_CHANNEL_ID,
            context.getString(R.string.notificaton_finish_channel_progress_title),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.getString(R.string.notificaton_finish_channel_progress_description)
            setShowBadge(true)
        }

        notificationManagerCompat.createNotificationChannels(
            listOf(notificationProgressChanel, notificationFinishChanel)
        )
    }

    fun cancelAllNotifications() {
        notificationManagerCompat.cancelAll()
    }

    fun notifyProgress(
        millisUntilFinished: Long,
        boilingTime: Long,
        eggType: EggBoilingType
    ) {
        if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            val notification = getProgressNotification(
                millisUntilFinished = millisUntilFinished,
                boilingTime = boilingTime,
                eggType = eggType,
            )
            NotificationManagerCompat
                .from(context)
                .notify(PROGRESS_NOTIFICATION_ID, notification)
        }
    }

    fun notifyBoilingFinished(eggType: EggBoilingType) {
        if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(IS_LAUNCHED_FROM_NOTIFICATION_KEY, true)
            }

            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE // Required for Android 12
            )

            val notification = NotificationCompat.Builder(context, FINISH_CHANNEL_ID)
                .setContentTitle(getNotificationTitle(eggType))
                .setContentText(context.getString(R.string.notificaton_progress_finish_message))
                .setCategory(Notification.CATEGORY_ALARM)
                .setSmallIcon(R.drawable.ic_timer_grey)
                .setLargeIcon(getNotificationIcon(eggType))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true) // Dismiss the notification after the user clicks it
                .build()

            NotificationManagerCompat
                .from(context)
                .notify(FINISH_NOTIFICATION_ID, notification)
        }
    }

    fun getProgressNotification(
        millisUntilFinished: Long,
        boilingTime: Long,
        eggType: EggBoilingType
    ): Notification {
        val progress = ((boilingTime - millisUntilFinished) / boilingTime.toFloat() * 100).toInt()

        return NotificationCompat.Builder(context, PROGRESS_CHANNEL_ID)
            .setContentTitle(getNotificationTitle(eggType))
            .setContentText(getNotificationMessage(millisUntilFinished))
            .setCategory(Notification.CATEGORY_PROGRESS)
            .setProgress(MAX_PROGRESS, progress, false)
            .setSmallIcon(R.drawable.ic_timer_grey)
            .setLargeIcon(getNotificationIcon(eggType))
            .setOngoing(true)
            .addAction(getNotificationAction())
            .build()
    }

    private fun getNotificationMessage(timeLeft: Long) = context.getString(
        R.string.notification_progress_message,
        convertMsToText(timeLeft)
    )

    private fun getNotificationTitle(eggType: EggBoilingType) = when (eggType) {
        EggBoilingType.SOFT -> context.getString(R.string.common_soft_boiled_eggs)
        EggBoilingType.MEDIUM -> context.getString(R.string.common_medium_boiled_eggs)
        EggBoilingType.HARD -> context.getString(R.string.common_hard_boiled_eggs)
    }

    private fun getNotificationIcon(eggType: EggBoilingType): Bitmap {
        val iconResId = when (eggType) {
            EggBoilingType.SOFT -> R.drawable.notification_egg_soft
            EggBoilingType.MEDIUM -> R.drawable.notification_egg_medium
            EggBoilingType.HARD -> R.drawable.notification_egg_hard
        }
        return BitmapFactory.decodeResource(context.resources, iconResId)
    }

    private fun getNotificationAction(): NotificationCompat.Action {
        val actionIcon = IconCompat.createWithResource(context, R.drawable.ic_cancel)
        val actionText = context.getString(R.string.notificaton_progress_action_cancel)

        val intent = Intent(context, TimerService::class.java)
            .putExtra(TimerService.ACTION_CANCEL, true)

        val pendingIntent = PendingIntent.getService(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Action.Builder(actionIcon, actionText, pendingIntent).build()
    }

    companion object {
        const val PROGRESS_NOTIFICATION_ID = 1
        private const val FINISH_NOTIFICATION_ID = 2
        private const val MAX_PROGRESS = 100
        private const val PROGRESS_CHANNEL_ID = "eggy_progress_channel_id"
        private const val FINISH_CHANNEL_ID = "eggy_finish_channel_id"
    }
}