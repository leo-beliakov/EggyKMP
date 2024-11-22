package com.leoapps.eggy.base.egg.domain

import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.progress.domain.model.TimerStatusUpdate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TimerHelperIosImpl : TimerHelper {
    override val timerUpdates: Flow<TimerStatusUpdate> = flowOf()

    override fun isTimerRunning(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getTimerSpecs(): Int? {
        TODO("Not yet implemented")
    }

    override fun stopTimer() {
        TODO("Not yet implemented")
    }

    override fun startTimer(boilingTime: Long, eggType: EggBoilingType) {
        TODO("Not yet implemented")
    }

}