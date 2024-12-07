package com.leoapps.eggy.timer

import com.leoapps.base.egg.domain.model.EggBoilingType

interface LiveActivityManager {
    val isRunning: Boolean
    fun stopLiveActivity()
    fun startLiveActivity(boilingTime: Long, type: EggBoilingType)
}