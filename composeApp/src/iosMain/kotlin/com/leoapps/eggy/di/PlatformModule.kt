package com.leoapps.eggy.di

import com.leoapps.eggy.base.egg.domain.TimerManager
import com.leoapps.eggy.timer.TimerManagerIosImpl
import com.leoapps.eggy.common.vibration.domain.VibrationManager
import com.leoapps.eggy.common.vibrator.VibrationManagerIosImpl
import com.leoapps.eggy.timer.LiveActivityManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


fun platformModule(
    activityManagerFactory: () -> LiveActivityManager
) = module {
    singleOf(::VibrationManagerIosImpl).bind(VibrationManager::class)
    singleOf(::TimerManagerIosImpl).bind(TimerManager::class)
    single { activityManagerFactory() }.bind(LiveActivityManager::class)
}