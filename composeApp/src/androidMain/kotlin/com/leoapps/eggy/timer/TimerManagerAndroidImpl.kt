package com.leoapps.eggy.timer

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.base.egg.domain.TimerManager
import com.leoapps.eggy.progress.domain.model.TimerStatusUpdate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TimerManagerAndroidImpl(
    private val context: Context
) : TimerManager {

    private val coroutineScope = CoroutineScope(Job())

    private var binder: TimerService.TimerBinder? = null

    private var boilingTime = 0L
    private var eggType = EggBoilingType.MEDIUM

    private val _timerUpdates = MutableSharedFlow<TimerStatusUpdate>()
    override val timerUpdates = _timerUpdates.asSharedFlow()

    override fun getTimerSpecs(): Int? = 0 //todo implement

    override fun isTimerRunning(): Boolean = TimerService.isRunning

    override fun startTimer(boilingTime: Long, eggType: EggBoilingType) {
        this.boilingTime = boilingTime
        this.eggType = eggType

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

    override fun stopTimer() {
        binder?.stopTimer()
//        coroutineScope.cancel() //todo double-check
    }
}