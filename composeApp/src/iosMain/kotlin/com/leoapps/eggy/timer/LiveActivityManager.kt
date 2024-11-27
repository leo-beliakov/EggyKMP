package com.leoapps.eggy.timer

interface LiveActivityManager {
    fun stopLiveActivity()
    fun startLiveActivity(boilingTime: Long)
}