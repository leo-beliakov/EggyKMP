package com.leoapps.eggy.timer

interface LiveActivityManager {
    val isRunning: Boolean
    fun stopLiveActivity()
    fun startLiveActivity(boilingTime: Long)
}