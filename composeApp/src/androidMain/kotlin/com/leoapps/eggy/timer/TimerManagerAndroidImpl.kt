package com.leoapps.eggy.timer

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.progress.domain.TimerSettingsRepository
import com.leoapps.eggy.progress.domain.model.TimerSettings
import com.leoapps.eggy.progress.domain.model.TimerStatusUpdate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class TimerManagerAndroidImpl(
    private val context: Context,
    private val timerSettingsRepository: TimerSettingsRepository,
) : TimerManager {

    private val coroutineScope = CoroutineScope(Job())

    private var binder: TimerService.TimerBinder? = null

    private val _timerUpdates = MutableSharedFlow<TimerStatusUpdate>()
    override val timerUpdates = _timerUpdates.asSharedFlow()

    override suspend fun isTimerScheduled() = TimerService.isRunning

    override fun startTimer(boilingTime: Long, eggType: EggBoilingType) {
        context.bindService(
            Intent(context, TimerService::class.java),
            object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    binder = service as? TimerService.TimerBinder
                    binder?.startTimer(boilingTime, eggType)
                    binder?.timerState?.onEach { update ->
                        _timerUpdates.emit(update)
                    }?.launchIn(coroutineScope)
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    binder = null
                }
            },
            BIND_AUTO_CREATE,
        )
    }

    override fun cancelTimer() {
        binder?.stopTimer()
//        coroutineScope.cancel() //todo double-check
    }

    // In Android we don't need to handle the following methods
    override fun onAppRelaunched() = Unit
    override fun onAppLaunchedFromNotification() = Unit
    override fun onAppLaunched() = Unit
}