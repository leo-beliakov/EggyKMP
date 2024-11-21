package com.leoapps.eggy.timer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import com.leoapps.eggy.R
import eggy.composeapp.generated.resources.Res
import eggy.composeapp.generated.resources.ic_timer_grey

class TimerService : Service() {

    companion object {
        var isRunning = false
            private set
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("MyTag", "TimerService onCreate ${hashCode()}")
        isRunning = true

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        val notificationProgressChanel = NotificationChannel(
            "eggy_progress_channel_id",
            "Test chanel",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Test chanel (description)"
            setShowBadge(false)
        }
        notificationManagerCompat.createNotificationChannel(notificationProgressChanel)

        ServiceCompat.startForeground(
            this,
            123, // Notification ID
            NotificationCompat.Builder(this, "eggy_progress_channel_id")
                .setContentTitle("Test Title")
                .setContentText("Test Content")
                .setCategory(Notification.CATEGORY_PROGRESS)
                .setProgress(100, 50, false)
                .setSmallIcon(R.drawable.ic_timer_grey)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_timer_grey))
                .build(),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            } else {
                0
            }
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(STOP_FOREGROUND_REMOVE)
        Log.d("MyTag", "TimerService onDestroy ${hashCode()}")
        isRunning = false
    }
}