package com.leoapps.eggy.timer

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import androidx.core.app.ServiceCompat
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.base.notification.BoilProgressNotificationManager
import com.leoapps.eggy.progress.domain.model.TimerStatusUpdate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

private const val TIMER_UPDATE_INTERVAL = 200L
private const val NOTIFICATION_UPDATE_INTERVAL = 1000L

class TimerService : Service() {

    private val notificationManager: BoilProgressNotificationManager by inject()

    private val coroutineScope = CoroutineScope(Job())

    private var timer: CountDownTimer? = null
    private val _timerState = MutableSharedFlow<TimerStatusUpdate>()

    private var boilingTime = 0L
    private var eggType = EggBoilingType.MEDIUM

    override fun onBind(intent: Intent?) = TimerBinder()

    override fun onCreate() {
        super.onCreate()
        isRunning = true
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.extras?.getBoolean(ACTION_CANCEL) == true) {
            onStopTimer()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun onStartTimer(
        boilingTime: Long,
        eggType: EggBoilingType,
    ) {
        this.boilingTime = boilingTime
        this.eggType = eggType

        notificationManager.cancelAllNotifications()
        startForegroundNotification()
        startTimer()
    }

    private fun startForegroundNotification() {
        ServiceCompat.startForeground(
            this,
            BoilProgressNotificationManager.PROGRESS_NOTIFICATION_ID,
            notificationManager.getProgressNotification(
                millisUntilFinished = boilingTime,
                boilingTime = boilingTime,
                eggType = eggType
            ),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            } else {
                0
            }
        )
    }

    private fun startTimer() {
        timer?.cancel()
        timer = twoTicksTimer(
            millisInFuture = boilingTime,
            shortTickInterval = TIMER_UPDATE_INTERVAL,
            longTickInterval = NOTIFICATION_UPDATE_INTERVAL,
            onShortTick = ::updateProgress,
            onLongTick = ::updateNotification,
            onTimerFinished = ::onTimerFinished,
        )
        timer?.start()
    }

    private fun updateProgress(millisUntilFinished: Long) {
        coroutineScope.launch {
            _timerState.emit(
                TimerStatusUpdate.Progress(
                    timePassedMs = boilingTime - millisUntilFinished
                )
            )
        }
    }

    private fun updateNotification(millisUntilFinished: Long) {
        notificationManager.notifyProgress(
            millisUntilFinished = millisUntilFinished,
            boilingTime = boilingTime,
            eggType = eggType
        )
    }

    private fun onTimerFinished() {
        notificationManager.notifyBoilingFinished(eggType)
        coroutineScope.launch {
            _timerState.emit(TimerStatusUpdate.Finished)
        }
        onStopTimer()
    }

    private fun onStopTimer() {
        timer?.cancel()
        coroutineScope.launch { _timerState.emit(TimerStatusUpdate.Canceled) }
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    inner class TimerBinder: Binder() {

        val timerState = _timerState.asSharedFlow()

        fun startTimer(boilingTime: Long, eggType: EggBoilingType) {
            onStartTimer(boilingTime, eggType)
        }

        fun stopTimer() {
            onStopTimer()
        }
    }

    companion object {
        val ACTION_CANCEL: String = "com.leoapps.eggy.timer.TimerService.ACTION_CANCEL"

        var isRunning = false
            private set
    }
}
