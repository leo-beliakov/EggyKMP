package com.leoapps.eggy.di

import com.leoapps.eggy.base.egg.domain.TimerHelper
import com.leoapps.eggy.timer.TimerHelperIosImpl
import com.leoapps.eggy.common.vibration.domain.VibrationManager
import com.leoapps.eggy.common.vibrator.VibrationManagerIosImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::VibrationManagerIosImpl).bind(VibrationManager::class)
    singleOf(::TimerHelperIosImpl).bind(TimerHelper::class)
}