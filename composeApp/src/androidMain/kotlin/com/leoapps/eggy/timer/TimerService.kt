package com.leoapps.eggy.timer

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.CountDownTimer
import androidx.core.app.ServiceCompat
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base.egg.domain.model.EggSize
import com.leoapps.base.egg.domain.model.EggTemperature
import com.leoapps.eggy.base.notification.BoilProgressNotificationManager
import com.leoapps.eggy.logs.domain.EggyLogger
import com.leoapps.eggy.progress.domain.TimerSettingsRepository
import com.leoapps.eggy.progress.domain.model.TimerStatusUpdate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.android.ext.android.inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

private const val TIMER_UPDATE_INTERVAL = 200L
private const val NOTIFICATION_UPDATE_INTERVAL = 1000L

class TimerService : Service() {

    private val notificationManager: BoilProgressNotificationManager by inject()
    private val timerSettingsRepository: TimerSettingsRepository by inject()
    private val logger: EggyLogger by inject()

    private val coroutineScope = CoroutineScope(Job())

    private var timer: CountDownTimer? = null
    private val _timerState = MutableSharedFlow<TimerStatusUpdate>()

    private var boilingTime = 0L
    private var eggType = EggBoilingType.MEDIUM

    override fun onBind(intent: Intent?) = TimerBinder()

    override fun onCreate() {
        super.onCreate()
        logger.i { "TimerService onCreate" }
        isRunning = true
    }

    override fun onDestroy() {
        super.onDestroy()
        logger.i { "TimerService onDestroy" }
        isRunning = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.extras?.getBoolean(ACTION_CANCEL) == true) {
            logger.i { "TimerService Canceled from Notification" }
            onCancelTimer()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun onStartTimer(
        boilingTime: Long,
        eggType: EggBoilingType,
    ) {
        this.boilingTime = boilingTime
        this.eggType = eggType

        coroutineScope.launch {
            val timerEndTime = Clock.System.now() + boilingTime.toDuration(DurationUnit.MILLISECONDS)
            timerSettingsRepository.saveTimerSettings(
                timerEndTime = timerEndTime,
                timerTotalTime = boilingTime,
                eggType = eggType,
                eggSize = EggSize.MEDIUM,
                eggTemperature = EggTemperature.ROOM,
            )
        }
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
        logger.i { "TimerService updateProgress ${millisUntilFinished / 1000}" }
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
        logger.i { "TimerService onTimerFinished" }
        coroutineScope.launch {
            notificationManager.notifyBoilingFinished(eggType)
            _timerState.emit(TimerStatusUpdate.Finished)
            finishTimer()
        }
    }

    private fun onCancelTimer() {
        coroutineScope.launch {
            _timerState.emit(TimerStatusUpdate.Canceled)
            finishTimer()
        }
    }

    private suspend fun finishTimer() {
        timer?.cancel()
        timerSettingsRepository.clearTimerSettings()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    inner class TimerBinder : Binder() {

        val timerState = _timerState.asSharedFlow()

        fun startTimer(boilingTime: Long, eggType: EggBoilingType) {
            onStartTimer(boilingTime, eggType)
        }

        fun stopTimer() {
            onCancelTimer()
        }
    }

    companion object {
        val ACTION_CANCEL: String = "com.leoapps.eggy.timer.TimerService.ACTION_CANCEL"

        var isRunning = false
            private set
    }
}
